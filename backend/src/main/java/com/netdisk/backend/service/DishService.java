package com.netdisk.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netdisk.backend.dto.DishDto;
import com.netdisk.backend.pojo.Dish;

public interface DishService extends IService<Dish> {
//    需要操控两张表 所以需要自己写方法
//    新增菜品的同时 还要插入 对应的口味数据  dish  dish_flavor
    public void  saveWithFlavor(DishDto dishDto);
}
