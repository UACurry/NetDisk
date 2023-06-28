package com.netdisk.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netdisk.backend.common.R;
import com.netdisk.backend.dto.SetmealDto;
import com.netdisk.backend.pojo.Category;
import com.netdisk.backend.pojo.Setmeal;
import com.netdisk.backend.pojo.SetmealDish;
import com.netdisk.backend.service.CategoryService;
import com.netdisk.backend.service.SetmealDishService;
import com.netdisk.backend.service.SetmealService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     */
    @PostMapping("/setmeal/")
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/setmeal/page/")
    public R<Page> page(int page,int pageSize, String name){
        // 分页构造器
        Page<Setmeal> pageinfo = new Page<>(page, pageSize);
//        只有 dto中 含有字段 name
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

//        添加条件
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageinfo,queryWrapper);

//        对象拷贝 不拷贝 records 因为泛型不一致 将其他的先拷贝过来
        BeanUtils.copyProperties(pageinfo, dtoPage, "records");
        List<Setmeal> records = pageinfo.getRecords();

//        用于赋值 categoryname
        List<SetmealDto> list = records.stream().map((item) ->{
            SetmealDto setmealDto = new SetmealDto();
//            将正常属性进行拷贝 因为下面只赋值 name
            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @DeleteMapping("/setmeal/")
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");

//        int index=0;
//        for(String id:ids) {
//            Setmeal setmeal = setmealService.getById(id);
//            if(setmeal.getStatus()!=1){
//                setmealService.removeById(id);
//            }else {
//                index++;
//            }
//        }
//        if (index>0&&index==ids.length){
//            return R.error("选中的套餐均为启售状态，不能删除");
//        }else {
//            return R.success("删除成功");
//        }
    }

    /**
     * 停售 起售
     */
    @PostMapping("/setmeal/status/{status}/")
    public R<String> pause(@PathVariable int status,String[] ids){
        for (String id:ids){
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("修改成功");
    }


    /**
     * 根据条件进行查询
     * @param setmeal
     * @return
     */
    @GetMapping("/setmeal/list/")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());

        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealService.list(queryWrapper);

        return R.success(setmealList);
    }
}
