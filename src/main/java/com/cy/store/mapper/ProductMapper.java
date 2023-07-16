package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.*;

public interface ProductMapper {

    /**
     * 根据商品的优先级排序选出最热销的四个商品
     * @return 前4名热销商品列表
     */
    List<Product> findHotList();

    /**
     * 根据商品的id查询商品的详细信息
     * @param id 商品的id
     * @return 返回商品的详细信息
     */
    Product findById(Integer id);
}
