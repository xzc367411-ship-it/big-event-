package com.example.demo.service.impl;

import com.example.demo.mapper.ArticleMapper;
import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;
import com.example.demo.service.ArticleService;
import com.example.demo.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class articleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {

        //补充属性
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public Article findById(Integer id) {
        Article ac=  articleMapper.findById(id);
        return ac;
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //1.创建PageBean对象
        PageBean<Article> pb =new PageBean<>();

        //2.开启分页查询 PageHellper
         PageHelper.startPage(pageNum,pageSize);
        //3.调用mapper
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list( userId,categoryId,state);
        Page<Article> p =(Page<Article>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
