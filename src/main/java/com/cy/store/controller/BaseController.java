package com.cy.store.controller;
import com.cy.store.controller.exception.*;
import com.cy.store.service.exception.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**控制层类的基类*/
public class BaseController {

    /**操作成功的状态码*/
    public static final int ok = 200;

    // 项目出现异常后，会被统一拦截到这个方法中，此方法此时就充当异常处理方法，方法的返回值给到前端
    // 请求处理方法，这个方法的返回值为传递给前端的数据JsonRsult
    // 自动将异常对象传递给此方法的参数列表上
    @ExceptionHandler({ServiceException.class,FileUploadException.class}) // 用于统一处理抛出的异常,参数列表可以为数组
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已经被占用的异常");
        }else if(e instanceof UsernameNotFoundException){
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("用户密码错误的异常");
        }else if (e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMessage("用户拥有收货地址条目数超出限制的异常");
        }else if (e instanceof AddressNotFoundException){
            result.setState(4004);
            result.setMessage("用户的收货地址数据不存在的异常");
        }else if (e instanceof AccessDeniedException){
            result.setState(4005);
            result.setMessage("收货地址数据非法访问的异常");
        }else if (e instanceof ProductNotFoundException){
            result.setState(4006);
            result.setMessage("商品数据不存在异常");
        }else if (e instanceof CartNotFoundException){
            result.setState(4007);
            result.setMessage("购物车不存在的异常");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生了未知的异常");
        }else if (e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("更新用户数据时发生未知的异常");
        }else if (e instanceof DeleteException){
            result.setState(5002);
            result.setMessage("删除用户数据时发生未知的异常");
        }else if (e instanceof FileEmptyException){
            result.setState(6000);
            result.setMessage("上传文件为空的异常");
        }else if (e instanceof FileSizeException){
            result.setState(6001);
            result.setMessage("上传文件大小超出限制的异常");
        }else if (e instanceof FileStateException){
            result.setState(6002);
            result.setMessage("上传文件状态异常");
        }else if (e instanceof FileTypeException){
            result.setState(6003);
            result.setMessage("上传文件类型错误异常");
        }else if (e instanceof FileUploadIOException){
            result.setState(6004);
            result.setMessage("上传文件IO流读取或存储异常");
        }


        return result;
    }

    // 会话层session数据uuid和username的获取，因为session数据的设置只有在第一次登录时才会用因此不设置在基类中
    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录用户uid的值
     */
    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
        //session中可以保存为任何类型,要先转为String在利用包装类的valueOf转换为Integer类型
    }
    /**
     * 获取当前登录用户的username
     * @param session session对象
     * @return 当前登录用户的用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
