package com.cy.store.service;


import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void findByParent(){
        // 86表示中国，所有省份的父代号都是86
        List<District> result = districtService.getByParent("86");
        for(District d:result){
            System.err.println(d);
        }
    }

    @Test
    public void getNameByCode(){
        System.out.println(districtService.getNameByCode("110101"));
    }
}
