package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(8);
        cart.setPid(10000001);
        cart.setPrice(1000L);
        cart.setNum(2);
        Integer rows = cartMapper.insert(cart);
        System.out.println(rows);

    }

    @Test
    public void updateNumByCid(){
        Integer rows = cartMapper.updateNumByCid(1, 10, "admin", new Date());
        System.out.println(rows);
    }

    @Test
    public void findByUidAndPid(){
        Cart result = cartMapper.findByUidAndPid(8, 10000001);
        System.out.println(result);
    }

    @Test
    public void findByUid(){
        List<CartProductVO> result = cartMapper.findByUid(8);
        System.out.println(result);
    }

    @Test
    public void findByCid(){
        Cart result = cartMapper.findByCid(1);
        System.out.println(result);
    }

    @Test
    public void findVOByCid(){
        Integer[] cids = new Integer[]{1,2,3,4};
        List<CartProductVO> result = cartMapper.findVOByCid(cids);
        System.out.println(result);
    }
}
