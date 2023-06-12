package com.netdisk.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.netdisk.backend.common.R;
import com.netdisk.backend.pojo.Employee;
import com.netdisk.backend.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee/login/")
//    RequestBody 用于接收
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//        查询与之姓名匹配的人
        queryWrapper.eq(Employee::getUsername, employee.getUsername());

//      用户名是唯一的
        Employee employee1 = employeeService.getOne(queryWrapper);

        if(employee1 == null){
            return R.error("登陆失败, 用户名未注册");
        }

//        如果查询到该用户 则比对密码 和加密后的数据进行比对
        if(!employee1.getPassword().equals(password)){
            return R.error("登陆失败，密码错误");
        }

//        查看账号状态是否可用
        if(employee1.getStatus() == 0){
            return R.error("登陆失败，账号禁用");
        }

//       登陆成功
        request.getSession().setAttribute("employee", employee1.getId());

        return R.success(employee1);
    }

    @PostMapping("/employee/logout/")
    public R<String> logout(HttpServletRequest request){
//        清理session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}
