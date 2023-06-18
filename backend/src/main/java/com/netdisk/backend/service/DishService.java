package com.netdisk.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netdisk.backend.dto.DishDto;
import com.netdisk.backend.pojo.Dish;

public interface DishService extends IService<Dish> {
//    需要操控两张表 所以需要自己写方法
//    新增菜品的同时 还要插入 对应的口味数据  dish  dish_flavor
    public void  saveWithFlavor(DishDto dishDto);

//    根据id 查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

//    更新菜品信息 同时更新口味信息
    public void updateWithFlavor(DishDto dishDto);
}
