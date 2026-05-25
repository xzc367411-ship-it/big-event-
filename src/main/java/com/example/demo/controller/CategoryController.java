package com.example.demo.controller;

import com.example.demo.pojo.Category;
import com.example.demo.pojo.Result;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated({Category.add.class}) Category category){
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cs= categoryService.list();
        return Result.success(cs);
    }

    @GetMapping("/detail")
    public Result<Category> detail(@RequestParam int id){
        Category cs =categoryService.detail(id);
        return Result.success(cs);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam int id){
        categoryService.delete(id);
        return Result.success();
    }
}
