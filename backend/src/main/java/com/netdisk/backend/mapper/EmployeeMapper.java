package com.netdisk.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netdisk.backend.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
