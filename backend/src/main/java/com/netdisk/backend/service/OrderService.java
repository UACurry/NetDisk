package com.netdisk.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netdisk.backend.pojo.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void sumit(Orders orders);
}
