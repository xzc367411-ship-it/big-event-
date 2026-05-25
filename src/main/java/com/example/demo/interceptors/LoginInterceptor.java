package com.example.demo.interceptors;

import com.example.demo.pojo.Result;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        try{
            //从redis中获得相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null) {
                //token已经失效了
                throw new RuntimeException();
            }
            Map<String,Object> claims= JwtUtil.parseToken(token);

            //把业务数据存储在ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
    }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空Threadlocal中的数据
        ThreadLocalUtil.remove();
    }
}
