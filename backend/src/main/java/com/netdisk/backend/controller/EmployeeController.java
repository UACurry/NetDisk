package com.netdisk.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netdisk.backend.common.R;
import com.netdisk.backend.pojo.Employee;
import com.netdisk.backend.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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

//    添加员工
    @PostMapping("/employee/")
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){

//        设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empID = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empID);
        employee.setUpdateUser(empID);

//        如果有错误 则要捕获异常 这是最简单的写法
//        try{
//            employeeService.save(employee);
//        }
//        catch (Exception exception){
//            R.error("新增员工失败");
//        }

//        这个使用全局的异常捕获
        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 员工分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
//    Page 是 mybatis plus 规定好的 里面又 records 和 total 正好也是前端需要的
    @GetMapping ("/employee/page/")
    public R<Page> page(int page, int pageSize, String name){

//        分页构造器
        Page pageInfo = new Page(page, pageSize);

//        条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        //添加一个条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);

//        添加一个排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 修改员工信息 比如禁用 和修改员工信息
     * @param employee
     * @return
     */
    @PutMapping("/employee/")
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long)request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id 查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/employee/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return  R.success(employee);
        }
        return R.error("没有该员工");
    }
}
