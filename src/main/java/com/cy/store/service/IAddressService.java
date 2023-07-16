package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;

import java.util.List;

/**用户收货地址数据业务层接口*/
public interface IAddressService {

    /**
     * 向数据库添加新的用户收货地址数据
     * @param address 用户收货地址数据
     * @param uid 传递用户的uid
     * @param username 传递用户的username主要用于modifiedUser，CreatedUser
     */
    void addNewAddress(Address address,Integer uid,String username);

    /**
     * 根据uid查询用户的收货地址
     * @param uid 用户的uid
     * @return 用户收货地址的list
     */
    List<Address> getByUid(Integer uid);

    /**
     * 修改某个用户的某条收货地址为默认收货地址
     * @param aid 收货地址aid值
     * @param uid 用户的uid值
     * @param username 用户的username
     */
    void setDefault(Integer aid,Integer uid,String username);

    /**
     * 删除某个用户的某个收货地址
     * @param aid 收货地址的aid值
     * @param uid 用户的uid值
     * @param username 用户的username值
     */
    void deleteAddress(Integer aid,Integer uid,String username);

}
