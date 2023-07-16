package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartProductVO;

import java.util.*;
import java.util.Date;

/**购物车持久层接口*/
public interface CartMapper {

    /**
     * 根据用户的uid和商品的pid来查询购物车数据信息
     * @param pid 商品的cid
     * @param uid 用户的uid(为了与cid进行匹配防止用户权限错误)
     * @return 受影响的行数
     */
    Cart findByUidAndPid(Integer uid,Integer pid);

    /**
     * 更新当前购物车商品数目
     * @param cid 购物车cid
     * @param num 更新的商品的数量
     * @param modifiedUser 更新人
     * @param modifiedTime 更新时间
     * @return 返回受影响的行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * VO:value object 当进行select查询时，查询结果属于多张表中的内容，此时发现结果集不能直接使用某个
     * POJO实体类接收，POJO不能包含多表查询的结果。解决方案是重新构建一个新的对象，用于存储所查询出来的
     * 结果集对应的映射所以把像这样的对象称之为值对象
     *
     * 根据用户的uid查询用户的购物车数据信息
     * @param uid 用户的uid
     * @return 返回购物车列表数据
     */
    List<CartProductVO> findByUid(Integer uid);

    /**
     * 根据cid值查询当前的购物车数据是否存在
     * @param cid 购物车cid
     * @return 返回购物车数据信息
     */
    Cart findByCid(Integer cid);

    /**
     * 根据购物车的cid信息查询VO对象
     * @param cids 购物车cid
     * @return 返回VO列表
     */
    List<CartProductVO> findVOByCid(Integer[] cids);
}
