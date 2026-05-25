package com.example.demo.service;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {
    //添加文章
    void add(Article article);
    //更新文章
    void update(Article article);
    //获取文章详情
    Article findById(Integer id);
    //删除文章
    void delete(Integer id);
    //条件分类列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
