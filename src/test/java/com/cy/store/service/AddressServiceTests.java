package com.cy.store.service;


import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("12345678901");
        address.setName("女朋友");

        addressService.addNewAddress(address,8,"admin");
    }

    @Test
    public void setDefault(){
        addressService.setDefault(5,8,"admin");
    }

    @Test
    public void deleteAddress(){
        addressService.deleteAddress(10,8,"admin");
    }
}
