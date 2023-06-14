package com.netdisk.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netdisk.backend.pojo.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
