package com.example.demo.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;              // ID
    @NotEmpty(groups ={Update.class, add.class})
    @Pattern(regexp = "^\\S{1,10}$",groups ={Update.class, add.class})
    private String categoryName;    // 分类名称
    @NotEmpty(groups ={Update.class, add.class})
    private String categoryAlias;   // 分类别名
    private Integer createUser;     // 创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 修改时间

    //分组校验
    public interface add extends Default {}
    public interface update extends Default {}
}