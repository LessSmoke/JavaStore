package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**用户收货地址控制层实现类*/
@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        // 获得用户的uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层方法
        addressService.addNewAddress(address,uid,username);

        return new JsonResult<>(ok);
    }

    @RequestMapping({"/",""})
    public JsonResult<List<Address>> getAddressesByUid(HttpSession session){

        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);

        return new JsonResult<>(ok,data);
    }
    // RestFul风格的请求编写
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        addressService.setDefault(aid,uid,username);
        return new JsonResult<>(ok);
    }

    @RequestMapping("{aid}/delete_address")
    public JsonResult<Void> deleteAddress(@PathVariable("aid") Integer aid,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        addressService.deleteAddress(aid,uid,username);
        return new JsonResult<>(ok);
    }
}
