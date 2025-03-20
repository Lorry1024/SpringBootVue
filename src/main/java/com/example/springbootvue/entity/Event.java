package com.example.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("events")
@Data
public class Event {
    @TableId(type = IdType.AUTO)       // 主键自增
    private Integer id;
    private String eventname;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")    //东八区
    private Date stday;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")    //东八区
    private Date enday;
    private String description;
    private String status;
    private Integer userid;             // 外键，关联用户表
}
