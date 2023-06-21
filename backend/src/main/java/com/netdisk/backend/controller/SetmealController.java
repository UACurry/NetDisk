package com.netdisk.backend.controller;


import com.netdisk.backend.common.R;
import com.netdisk.backend.dto.SetmealDto;
import com.netdisk.backend.service.SetmealDishService;
import com.netdisk.backend.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐管理
 */
@Slf4j
@RestController
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     */
    @PostMapping("/setmeal/")
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }
}
