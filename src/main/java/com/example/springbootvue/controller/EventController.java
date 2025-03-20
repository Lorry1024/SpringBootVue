package com.example.springbootvue.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootvue.common.Result;
import com.example.springbootvue.entity.Event;
import com.example.springbootvue.mapper.EventMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    @Resource
    EventMapper eventMapper;
    @ResponseBody
    @PostMapping
    public Result<?> save(@RequestBody Event Event){        //插入事件
//        System.out.println(Event);
        eventMapper.insert(Event);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Event event){  //更新一个事件的所有信息
        eventMapper.updateById(event);
        System.out.println(event);
        return Result.success();
    }

    @PutMapping("status")
    public Result<?> updateStatus(@RequestBody Event event){  //更新一个事件的状态
        System.out.println(event);
        System.out.println(event.getStatus().equals("已完成"));
        if(event.getStatus().equals("已完成")){
            System.out.println("!!!");
            event.setStatus("进行中");
        }else{
            event.setStatus("已完成");
        }
        eventMapper.updateById(event);
        System.out.println(event);
        return Result.success();
    }


    @GetMapping     //按页查找事件
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "0") Integer userId){
        System.out.println(userId);
        LambdaQueryWrapper<Event> wrapper = Wrappers.<Event>lambdaQuery().eq(Event::getUserid,userId);      //一个用户的事件
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Event::getEventname,search);
        }
        Page<Event> eventPage = eventMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(eventPage);
    }

    @DeleteMapping("/{id}")                         //删除一个事件
    public Result<?> delete(@PathVariable Integer id){
        eventMapper.deleteById(id);
        return Result.success();
    }
}
