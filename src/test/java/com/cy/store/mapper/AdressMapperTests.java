package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdressMapperTests {

    @Autowired
    private AddressMapper adressMapper;

    @Test
    public void insertAdress(){
        Address adress = new Address();
        adress.setUid(8);
        adress.setPhone("12345678901");
        adress.setName("yujunxin");

        adressMapper.insertAddress(adress);
    }

    @Test
    public void countByUid(){
        Integer uid = 8;
        Integer rows = adressMapper.countByUid(8);
        System.out.println(rows);
    }

    @Test
    public void findByUid(){
        List<Address> list = adressMapper.findByUid(8);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        Address result = adressMapper.findByAid(5);
        System.out.println(result);
    }

    @Test
    public void updateNonDefault(){
        Integer rows = adressMapper.updateNonDefault(8);
        System.out.println(rows);
    }

    @Test
    public void updateIsDefaultByAid(){
        Integer rows = adressMapper.updateIsDefaultByAid(6, "admin", new Date());
        System.out.println(rows);
    }

    @Test
    public void deleteByAid(){
        Integer rows = adressMapper.deleteByAid(8);
        System.out.println(rows);
    }

    @Test
    public void findLastModified(){
        Address result = adressMapper.findLastModified(8);
        System.out.println(result);
    }
}
