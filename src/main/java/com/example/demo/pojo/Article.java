package com.example.demo.pojo;

import com.example.demo.anno.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;             // ID
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;           // 文章标题
    @NotEmpty
    private String content;         // 文章内容
    @URL
    @NotEmpty
    private String coverImg;        // 封面图片
    @State
    private String state;           // 状态：已发布/草稿
    @NotNull
    private Integer categoryId;     // 分类ID
    private Integer createUser;     // 创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 修改时间
}