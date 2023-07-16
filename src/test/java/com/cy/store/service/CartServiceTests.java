package com.cy.store.service;


import com.cy.store.entity.District;
import com.cy.store.vo.CartProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void addIntoCart(){
        Integer uid = 8;
        Integer pid = 10000001;
        Integer amount = 6;
        String username = "admin";

        cartService.addIntoCart(uid,pid,amount,username);
    }

    @Test
    public void showCartByUid(){
        List<CartProductVO> result = cartService.showCartByUid(8);
        System.out.println(result);

    }

    @Test
    public void addNumByCid(){
        Integer amount = cartService.addNumByCid(1, 8, "admin");
        System.out.println(amount);
    }
}
