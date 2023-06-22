package com.netdisk.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netdisk.backend.dto.SetmealDto;
import com.netdisk.backend.pojo.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐 同时保存和套餐和菜品的关联关系 涉及到两张表
     */
    public void saveWithDish(SetmealDto setmealDto);

//    删除套餐 同时删除套餐和菜品关联数据
    public void removeWithDish(List<Long> ids);
}
