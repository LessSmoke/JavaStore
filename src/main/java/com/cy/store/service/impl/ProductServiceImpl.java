package com.cy.store.service.impl;

import com.cy.store.entity.Product;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.IProductService;
import com.cy.store.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        //直接调用productMapper层方法查询
        List<Product> list = productMapper.findHotList();
        for(Product item:list){
            item.setPriority(null);
            item.setCreatedUser(null);
            item.setCreatedTime(null);
            item.setModifiedTime(null);
            item.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public Product findById(Integer id) {
        Product result = productMapper.findById(id);
        if(result==null){
            throw new ProductNotFoundException("商品信息不存在异常");
        }
        //简化传递数据
        result.setPriority(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedTime(null);
        result.setModifiedUser(null);
        return result;
    }
}
