package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.dto.DishDto;
import com.netdisk.backend.mapper.DishMapper;
import com.netdisk.backend.pojo.Dish;
import com.netdisk.backend.pojo.DishFlavor;
import com.netdisk.backend.service.DishFlavorService;
import com.netdisk.backend.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    //    新增菜品的同时 还要插入 对应的口味数据  dish  dish_flavor
    @Override
//    由于涉及到多张表 需要加这个注解  事务的支持
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
//        保存菜品的基本信息  因为 dishDto 继承自 Dish  所以可以保存 Dish 的字段
        this.save(dishDto);

//        菜品id 因为 dishflavor表 里面需要这个字段
        Long dishId = dishDto.getId();

//        菜品口味的集合
        List<DishFlavor> flavors = dishDto.getFlavors();

//        为 flavors 中 DishFlavor 实体的dishid附上值   用 stream流循环遍历
        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());


//        但是菜品口味还没有保存 需要保存到 Dish_flaor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     *   根据id 查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {

//        查询菜品基本信息 来自 dish表
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
//        对象拷贝  除了 falover 其余都拷贝
        BeanUtils.copyProperties(dish,dishDto);

//        查询口味信息 从 dish——flavor 表
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());

        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

//        单独赋值
        dishDto.setFlavors(flavors);

        return dishDto;
    }

//    更新操作
    @Override
    //    由于涉及到多张表 需要加这个注解  事务的支持
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {

//        更新dish基本信息  dishDto 继承自 dish 也就是更新dish表
        this.updateById(dishDto);

//        清理当前菜品的口味数据 --dish_flavor delete
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

//        添加新的口味数据 --dish_flavor insert
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}
