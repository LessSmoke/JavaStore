package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.exception.AccessDeniedException;
import com.cy.store.service.exception.CartNotFoundException;
import com.cy.store.service.exception.InsertException;
import com.cy.store.service.exception.UpdateException;
import com.cy.store.vo.CartProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    /** 购物车的业务层依赖于购物车持久层和商品持久层的方法*/
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addIntoCart(Integer uid, Integer pid, Integer amount, String username) {
        // 判断该账户下是否已经存在一个购物车装有pid对应的商品
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if(result!=null){
            // result不为空说明已经存在了该商品，那么只需要更新商品数量即可
            Integer rows = cartMapper.updateNumByCid(result.getCid(), amount, username, new Date());
            if(rows!=1){
                throw new UpdateException("更新数据时产生未知异常");
            }
        }else{
            // result 为空说明并不存在该购物车，那么就创建新的购物车
            Cart cart = new Cart();
            // 根据商品的pid查询商品信息
            Product product = productMapper.findById(pid);
            //补全信息
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setPrice(product.getPrice());
            cart.setNum(amount);
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());

            //调用mapper层接口方法插入数据
            Integer rows = cartMapper.insert(cart);
            if(rows!=1){
                throw new InsertException("在插入数据时产生了未知的异常");
            }
        }

    }

    @Override
    public List<CartProductVO> showCartByUid(Integer uid) {
        List<CartProductVO> result = cartMapper.findByUid(uid);
        return result;
    }

    @Override
    public Integer addNumByCid(Integer cid, Integer uid, String username) {
        // 查询当前数据是否存在对应的购物车数据以及查询到的数据是否有访问权限
        Cart result = cartMapper.findByCid(cid);
        if(result==null){
            throw new CartNotFoundException("当前购物车信息不存在");
        }
        else if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("无访问权限");
        }

        // 如果存在的话根据当前cid更新数据
        Integer rows = cartMapper.updateNumByCid(cid, result.getNum()+1 ,username, new Date());
        if(rows!=1){
            throw new UpdateException("更新数据时发生了未知的异常");
        }
        result = cartMapper.findByCid(cid);
        return result.getNum();
    }

    @Override
    public List<CartProductVO> getVOByCid(Integer[] cids, Integer uid) {
        List<CartProductVO> list = cartMapper.findVOByCid(cids);
        Iterator<CartProductVO> iterator = list.iterator();
        while (iterator.hasNext()){
            CartProductVO cartProductVO = iterator.next();
            if(!cartProductVO.getUid().equals(uid)){ // 这一条数据并不属于当前用户
                list.remove(cartProductVO);//从集合中移除这个元素
            }
        }
        return list;
    }


}
