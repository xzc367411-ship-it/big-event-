package com.example.demo.service.impl;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.pojo.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        //补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);

    }

    @Override
    public List<Category> list() {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        return categoryMapper.list(userId);
    }

    @Override
    public Category detail(int id) {
        Category c= categoryMapper.findById(id);
        return c;
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.delete(id);
    }


}
