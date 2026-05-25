package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    @NotNull(message = "id不能为空")
    private  Integer id;
    private  String username;
    @JsonIgnore//让让springmvc把当前对象转化为Json字符串的时候，忽略password
    private  String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private  String nickname;

    @NotEmpty
    @Email
    private  String email;
    private  String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
