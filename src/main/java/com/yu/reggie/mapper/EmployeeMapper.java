package com.yu.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.reggie.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {


}
