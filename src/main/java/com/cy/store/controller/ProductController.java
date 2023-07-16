package com.cy.store.controller;

import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;

    @RequestMapping("show_hot_list")
    public JsonResult<List<Product>> showHotList(){
        List<Product> data = productService.findHotList();
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("{id}/details")
    public JsonResult<Product> showProductDetails(@PathVariable("id") Integer id){
        Product data = productService.findById(id);
        return new JsonResult<>(ok,data);
    }
}
