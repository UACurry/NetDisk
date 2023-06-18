package com.netdisk.backend.dto;


import com.netdisk.backend.pojo.Setmeal;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<Setmeal> setmealDishes;

    private String categoryName;
}
