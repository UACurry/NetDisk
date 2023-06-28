package com.netdisk.backend.dto;

import com.netdisk.backend.pojo.Dish;
import com.netdisk.backend.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

// 当前端数据 传输的数据 一个实体无法包含 含有其他实体的字段  所以需要定义一个Dto类

// 用于增加菜品的 数据传输
@Data
public class DishDto extends Dish {

//    菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
