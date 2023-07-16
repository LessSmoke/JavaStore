package com.cy.store.mapper;

import com.cy.store.entity.Address;

import java.util.Date;
import java.util.List;

/**收货地址持久层接口*/
public interface AddressMapper {
    /**
     * 插入用户的收货地址数据
     * @param adress 收货地址数据
     * @return 受影响的行数
     */
    Integer insertAddress(Address adress);

    /**
     * 根据用户的uid计算用户当前存储在数据库的收货地址条目数
     * @param uid 用户的uid
     * @return 用户数据库存储的收货地址条目数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的uid查询用户的收货地址列表
     * @param uid 用户的uid
     * @return 返回用户收货地址列表
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据是否存在
     * @param aid 收货地址对应的aid
     * @return 返回查询到的收货地址数据信息，如果没有找到返回null
     */
    Address findByAid(Integer aid);

    /**
     * 根据uid将用户所有的收货地址is_default都设置为0
     * @param uid 用户的uid
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据收货地址的aid的
     * @param aid 收货地址数据的aid
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateIsDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 根据收货地址的aid来删除收货地址数据
     * @param aid 收货地址的aid
     * @return 返回受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据uid查询当前用户最后一次被修改的收货地址
     * @param uid 用户的uid
     * @return 最后一次被修改的收货地址的数据信息
     */
    Address findLastModified(Integer uid);
}
