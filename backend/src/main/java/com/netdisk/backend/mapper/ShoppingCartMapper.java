package com.netdisk.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netdisk.backend.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
