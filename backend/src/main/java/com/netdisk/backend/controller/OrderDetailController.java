package com.netdisk.backend.controller;

import com.netdisk.backend.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

}
