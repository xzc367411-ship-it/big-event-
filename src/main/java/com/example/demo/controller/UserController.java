package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.MD5Util;
import com.example.demo.utils.ThreadLocalUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){

            //查询用户
            User u = userService.findByUserName(username);
            //注册
            if (u == null) {
                userService.register(username, password);
                return Result.success();
            } else {
                return Result.error("用户名已被占用");
            }

    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        //查询用户
        User loginUser = userService.findByUserName(username);
        //判断用户名是否存在
        if(loginUser == null){
            return Result.error("用户名不存在");
        }
        //判断密码是否正确
        if(MD5Util.md5(password).equals(loginUser.getPassword())){
            //登入成功
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            //把token存储到redis中
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token,token,12, TimeUnit.HOURS);

            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name="Authorization") String token*/){
       //根据用户名查询用户
        /*Map<String, Object> map = JwtUtil.parseToken(token);
        String username =(String) map.get("username");*/

        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user =userService.findByUserName(username);
        return Result.success(user);

    }
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(rePwd)){
            return Result.error("密码不能为空");
        }
        //原密码是否正确
        //调用Service根据用户名查找原密码再与oldpwd对比
        Map<String,Object> map = ThreadLocalUtil.get();
        String username =(String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if(!loginUser.getPassword().equals(MD5Util.md5(oldPwd))){
            return Result.error("原密码输入不正确");
        }

        //校验newpwd repwd 是否一致
        if (!rePwd.equals(newPwd)){
            return Result.error("两次输入密码不一样");
        }
        //2.调用service完成密码更新
        userService.updatePwd(newPwd);

        //删除redis中对应的token
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.getOperations().delete(token);
        return Result.success();
    }
}
