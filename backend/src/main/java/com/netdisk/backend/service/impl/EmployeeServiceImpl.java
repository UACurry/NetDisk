package com.netdisk.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netdisk.backend.common.R;
import com.netdisk.backend.mapper.EmployeeMapper;
import com.netdisk.backend.pojo.Employee;
import com.netdisk.backend.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

//    public R<Employee> login_ser(String username, String password) {
//
////        String password = employee.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
//
//        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
//
//        queryWrapper.eq("username", username);
//
////      用户名是唯一的
//        Employee employee1 = employeeMapper.selectOne(queryWrapper);
//
//        if(employee1 == null){
//            return R.error("登陆失败");
//        }
//
////        如果查询到该用户 则比对密码 和加密后的数据进行比对
//        if(!employee1.getPassword().equals(password)){
//            return R.error("登陆失败");
//        }
//
////        查看账号状态是否可用
//        if(employee1.getStatus() == 0){
//            return R.error("账号禁用");
//        }
//
////       登陆成功
//        request.getSession().setAttribute("employee", employee1.getId());
//        return R.success(employee1);
//    }

}
