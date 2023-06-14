package com.netdisk.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netdisk.backend.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
