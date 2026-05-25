package com.example.demo.controller;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Result;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

 /*   @GetMapping("/list")
    public Result<String> list(*//*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*//*) {
    *//*    //验证token
        try{
            Map<String,Object> claims= JwtUtil.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace(); // ⭐ 加这个
            response.setStatus(401);
            return Result.error("未登入");
        }*//*
        return Result.success("所有文章信息");
    }*/


    //新增文章
    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }
    @PutMapping
    //更新文章
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }
    //获取文章详情
    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam Integer id){
        Article ac= articleService.findById(id);
        return Result.success(ac);

    }

    //删除文章
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        articleService.delete(id);
        return Result.success();
    }
     @GetMapping
    //文章列表 （条件分页）
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false)String state
     ){
        PageBean<Article> pb= articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }
}
