package com.cy.store.controller;

import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import javax.servlet.http.HttpSession;

/**购物车数据控制层实体类*/
@RestController //标签当前类交给springboot处理
@RequestMapping("carts") // 定义该类请求路径
public class CartController extends BaseController {

    @Autowired
    private ICartService cartService;

    @RequestMapping("add_into_cart")
    public JsonResult<Void> addIntoCart(Integer pid, Integer amount, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        //调用业务层接口方法进行插入数据操作
        cartService.addIntoCart(uid,pid,amount,username);

        return new JsonResult<>(ok);
    }

    @RequestMapping("show_cart")
    public JsonResult<List<CartProductVO>> showCart(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartProductVO> data = cartService.showCartByUid(uid);
        return new JsonResult<>(ok,data);
    }
    @RequestMapping("{cid}/add")
    public JsonResult<Integer> addNumByCid(@PathVariable("cid") Integer cid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer data = cartService.addNumByCid(cid,uid,username);
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("list")
    public JsonResult<List<CartProductVO>> getVOByCid(Integer[] cids,HttpSession session){
        List<CartProductVO> data = cartService.getVOByCid(cids, getUidFromSession(session));
        return new JsonResult<>(ok,data);
    }
}
