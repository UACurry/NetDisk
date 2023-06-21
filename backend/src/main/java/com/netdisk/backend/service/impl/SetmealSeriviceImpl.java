package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.dto.SetmealDto;
import com.netdisk.backend.mapper.SetmealMapper;
import com.netdisk.backend.pojo.Setmeal;
import com.netdisk.backend.pojo.SetmealDish;
import com.netdisk.backend.service.SetmealDishService;
import com.netdisk.backend.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealSeriviceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐 同时保存和套餐和菜品的关联关系 涉及到两张表
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        // 保存套餐基本信息  保存 setmeal 因为dto继承自 setmeal
        this.save(setmealDto);

//        其中 setmealdish中   setmeaid 是空的
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

//        保存关联信息
        setmealDishService.saveBatch(setmealDishes);
    }
}
