package com.cy.store.service;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartProductVO;

import java.util.*;

/**购物车数据业务层接口*/
public interface ICartService {

    /**
     * 将商品添加进入购物车
     * @param uid 用户的id
     * @param pid 商品的id
     * @param amount 商品的数量
     * @param username 用户的username
     */
    void addIntoCart(Integer uid,Integer pid,Integer amount,String username);

    /**
     *根据用户的id查询当前用户的购物车内都有哪些商品
     * @param uid 用户的uid
     * @return 用户对应的购物车内有哪些商品及具体的商品信息和购物车信息
     */
    List<CartProductVO> showCartByUid(Integer uid);

    /**
     * 根据当前购物车cid来更新当前购物车内商品数目信息
     * @param cid 当前购物车cid
     * @param uid 用户的uid
     * @param username 用户的uername
     *
     */
    Integer addNumByCid(Integer cid,Integer uid,String username);

    /**
     *
     * @param cids
     * @param uid
     * @return
     */
    List<CartProductVO> getVOByCid(Integer[] cids,Integer uid);
}
