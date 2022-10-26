package com.yu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.reggie.common.R;
import com.yu.reggie.domain.Employee;
import com.yu.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1获取密码，md5加密
        String password = employee.getPassword();
//        log.info(password);
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        log.info(password);

        //2.安唯一标识查找
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);
        if(emp == null){
            return R.error("查无此人！");
        }
        //3.匹对密码
        if (!emp.getPassword().equals(password)){
           return R.error("密码错误！");
        }
        if(!(emp.getStatus()==0)){
            request.getSession().setAttribute("employee",emp.getId());
            return R.success(emp);
        }
        return R.error("此员工已被禁用！登陆失败");
//        request.getSession().setAttribute("employee",emp.getId());
//        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<String> loginOut(HttpServletRequest request){
        //获取session清楚session中的数据
        request.getSession().removeAttribute("employee");

        return R.success("退出成功！");
    }
//    @GetMapping
//    public R<Page> employeeIPage(@RequestBody ){
//       Page page1= new Page<>(page,pageSize);
//        IPage<Employee> iPage = employeeService.page(page1,null);
//      return R.success(page1);
//    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工信息："+employee.toString());
        //设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        //获取当前登录用户的id
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        try {
            employeeService.save(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success("新员工添加成功！");
//        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Employee::getUsername,employee.getUsername());
//        Employee employee1= employeeService.getOne(wrapper);
//      if (employee1 == null){
//          boolean issave= employeeService.save(employee);
//          if (issave){
//              return R.success("添加成功");
//          }
//          return R.error("添加失败");
//      }
//      return R.error("用户名已存在！");
      }
      @PutMapping
      public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
//        Long emId= (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(emId);
        employeeService.updateById(employee);
        return R.success("员工修改成功！");
      }
      @GetMapping("/epage")
    public R<Page> page(int page,int pageSize,String name){
        //创建page分页构造器
          Page pageInfo = new Page(page,pageSize);
          //创建条件过滤器
          LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
          //设置条件是否为真，假就不添加
          queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
          //排序
          queryWrapper.orderByDesc(Employee::getUpdateTime);
          //执行查询
          employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
      }
//      @GetMapping("/{id}")
//    public R<String> getById(@PathVariable Integer id){
//        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Employee::getId,id);
//        Employee employee = employeeService.getOne(queryWrapper);
//        if (employee!= null){
//            return R.success("45");
//        }
//
//        return R.error("查无此人！");
//      }

}
