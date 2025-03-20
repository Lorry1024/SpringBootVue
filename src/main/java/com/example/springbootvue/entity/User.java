package com.example.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("users")
@Data
public class User {
    @TableId(type = IdType.AUTO)       // 主键自增
    private Integer id;
    private String username;
    private String password;
//    @TableField("nick_name")
    private String nickname;
    private Integer age;
    private String sex;
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")    //东八区
    private Date birthday;

    private String avatar;
}
