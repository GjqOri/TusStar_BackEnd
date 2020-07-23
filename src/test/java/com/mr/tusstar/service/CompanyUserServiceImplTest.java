package com.mr.tusstar.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Qi
 * @create 2020-07-21 15:45
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyUserServiceImplTest {

    @Autowired
    private CompanyUserService companyUserService;

    @Test
    public void querySaltByEmailTest() {
        System.out.println(companyUserService.querySaltByEmail("863768060@qq.com"));
    }

}
