package com.netdisk.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netdisk.backend.common.R;
import com.netdisk.backend.pojo.Category;
import com.netdisk.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping("/category/")
//    前端返回值  只用了一个 code  所以返回值为 String
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);

//        若菜品名字重复 会进入全局异常处理器
        return R.success("新增分类成功");
    }

    @GetMapping("/category/page/")
    public R<Page> page(int page, int pageSize){
//      分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

//        条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

//        根据sort升序排序
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

//    根据id删除菜品分类
    @DeleteMapping("/category/")
    public R<String> delete(Long id){
//        直接使用继承的类 进行删除
//        categoryService.removeById(id);

//        使用 impl中自定义方法进行删除
        categoryService.remove(id);

        return R.success("分类信息删除成功");
    }

    @PutMapping("/category/")
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改信息成功");
    }
}
