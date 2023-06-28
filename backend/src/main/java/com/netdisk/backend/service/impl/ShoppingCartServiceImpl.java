package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.mapper.ShoppingCartMapper;
import com.netdisk.backend.pojo.ShoppingCart;
import com.netdisk.backend.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
