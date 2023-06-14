package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.mapper.SetmealMapper;
import com.netdisk.backend.pojo.Setmeal;
import com.netdisk.backend.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealSeriviceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
