package com.cy.store.service;

import com.cy.store.entity.Product;

import java.util.*;

/**商品信息业务层接口*/
public interface IProductService {

    /**
     * 查询前四名热销商品
     * @return 热销商品集合
     */
    List<Product> findHotList();

    /**
     * 根据商品的id查询商品的详细信息
     * @param id 商品的id
     * @return 返回商品的详细信息
     */
    Product findById(Integer id);
}
