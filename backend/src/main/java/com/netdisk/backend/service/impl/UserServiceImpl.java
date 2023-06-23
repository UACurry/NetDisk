package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.mapper.UserMapper;
import com.netdisk.backend.pojo.User;
import com.netdisk.backend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
