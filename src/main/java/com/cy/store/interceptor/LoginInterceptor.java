package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

/**定义一个拦截器拦截未登录过的访问请求*/
public class LoginInterceptor implements HandlerInterceptor {

    // 底层实现AOP

    // 在调用所有处理请求的方法之前被自动调用执行的方法

    /**
     * 检测全局session对象中是否有uid数据，如果有放行，如果没有重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(url+controller:映射)
     * @return 如果返回值为true表示放行当前的请求，如果返回值为false则表示拦截当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 通过request对象来获取全局session对象
        Object uid = request.getSession().getAttribute("uid");
        if(uid == null){
            //说明用户没有登陆过系统，重定向到login.html页面
            response.sendRedirect("/web/login.html");
            //结束后续的调用
            return false;
        }
        return true;
    }

    // 在ModelAndView对象返回之后被自动调用的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 在整个请求所有关联的资源被执行完毕后，最后所执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
