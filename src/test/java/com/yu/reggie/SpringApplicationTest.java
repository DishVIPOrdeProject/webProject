package com.yu.reggie;

import com.yu.reggie.domain.Employee;
import com.yu.reggie.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
public class SpringApplicationTest {
    @Autowired
    private EmployeeMapper mappers;
    @Test
    void test1(){
//        Employee employee =mappers.findbyID1();

    }
}
