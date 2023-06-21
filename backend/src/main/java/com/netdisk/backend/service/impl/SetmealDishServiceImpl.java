package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.dto.SetmealDto;
import com.netdisk.backend.mapper.SetmealDishMapper;
import com.netdisk.backend.pojo.SetmealDish;
import com.netdisk.backend.service.SetmealDishService;
import com.netdisk.backend.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
