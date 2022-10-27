package com.yu.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.reggie.domain.Employee;
import com.yu.reggie.mapper.EmployeeMapper;
import com.yu.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
//    @Autowired
//    private EmployeeMapper employeeMapper;
//    public void save(){
//        employeeMapper.
//    }
}
