package com.netdisk.backend.controller;

import com.netdisk.backend.common.R;
import com.netdisk.backend.pojo.Orders;
import com.netdisk.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/submit/")
    public R<String> submit(@RequestBody Orders orders){
        orderService.sumit(orders);
        return R.success("下单成功");
    }
}
