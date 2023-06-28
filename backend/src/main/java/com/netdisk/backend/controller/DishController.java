package com.netdisk.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netdisk.backend.common.R;
import com.netdisk.backend.dto.DishDto;
import com.netdisk.backend.dto.SetmealDto;
import com.netdisk.backend.pojo.*;
import com.netdisk.backend.service.CategoryService;
import com.netdisk.backend.service.DishFlavorService;
import com.netdisk.backend.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping("/dish/")
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/dish/page/")
    public R<Page> page(int page, int pageSize, String name){
        //分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        // dish 表中 只有 category id  所以需要长训 category表 将 id 和具体名字对应  所以需要 使用DishDto
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Dish::getName, name);

//        排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

//        分页查询
        dishService.page(pageInfo, queryWrapper);

//        dish 表中 只有 category id  所以需要长训 category表 将 id 和具体名字对应  所以需要 使用DishDto

//        对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

//            分类id
            Long categotyId = item.getCategoryId();
            Category category = categoryService.getById(categotyId);

            if(category != null){
                String categoryname = category.getName();
                dishDto.setCategoryName(categoryname);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id 查询菜品信息 和 口味信息 使用 DishDto
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    public R<DishDto> edit(@PathVariable Long id){
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    /**
     * 修改菜品 同时注意 还需要更新口味表
     * @param dishDto
     * @return
     */
    @PutMapping("/dish/")
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    /**
     * 根据 categoriesid返回有哪些对应的菜品
     *
     * 后续改进 在用户端 要返回菜品 同时也要返回菜品的口味 但是Dish中没有口味信息
     */
//    @GetMapping("/dish/list/")
//    public R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//
////        只查起售的  getStatus 为 1的
//        queryWrapper.eq(Dish::getStatus, 1);
//
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = dishService.list(queryWrapper);
//
//        return R.success(list);
//    }

    /**
     * 根据 categoriesid返回有哪些对应的菜品
     *
     * 后续改进 在用户端 首页获取有哪些菜品时候 同时也要返回菜品的口味 但是Dish中没有口味信息
     * 所以需要使用DishDto 来保证有口味数据
     */
    @GetMapping("/dish/list/")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());

//        只查起售的  getStatus 为 1的
        queryWrapper.eq(Dish::getStatus, 1);

        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

//            分类id
            Long categotyId = item.getCategoryId();
            Category category = categoryService.getById(categotyId);

            if(category != null){
                String categoryname = category.getName();
                dishDto.setCategoryName(categoryname);
            }

//            菜品的id
            Long dishId = item.getId();

//            查询id对应的口味
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
//            dishID的 口味的集合
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }

}
