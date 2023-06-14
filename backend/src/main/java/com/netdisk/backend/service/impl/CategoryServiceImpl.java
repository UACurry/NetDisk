package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.common.CustomException;
import com.netdisk.backend.mapper.CategoryMapper;
import com.netdisk.backend.pojo.Category;
import com.netdisk.backend.pojo.Dish;
import com.netdisk.backend.pojo.Setmeal;
import com.netdisk.backend.service.CategoryService;
import com.netdisk.backend.service.DishService;
import com.netdisk.backend.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 删除之前进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

//      添加查询条件 根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count_dish = dishService.count(dishLambdaQueryWrapper);

//        查询当前分类是否关联了菜品 如果已经关联 抛出一个业务异常
        if(count_dish > 0){
//            主动抛出一个异常 而不是 由系统抛出 是由人为抛出
            throw new CustomException("当前分类项 关联了菜品 不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count_setmeal = setmealService.count(setmealLambdaQueryWrapper);

        //查询当前分类是否关联了套餐 如果已经关联 抛出一个业务异常
        if(count_setmeal > 0){
            throw new CustomException("当前分类项 关联了套餐 不能删除");
        }

//       如果都没关联 就正常删除
        super.removeById(id);
    }
}
