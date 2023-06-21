package com.netdisk.backend.dto;


import com.netdisk.backend.pojo.Setmeal;
import com.netdisk.backend.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
