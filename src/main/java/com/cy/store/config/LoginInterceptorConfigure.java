package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
/**完成处理器拦截器的注册，否则自定义拦截器不能使用*/
@Configuration //有这个注解Springboot会自动加载此配置类
public class LoginInterceptorConfigure implements WebMvcConfigurer {
    // 创建自定义的拦截器对象
    HandlerInterceptor interceptor = new LoginInterceptor();
    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置白名单：存放在一个List集合
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("css/**");
        patterns.add("images/**");
        patterns.add("js/**");
        // 放行部分web中的静态资源
        patterns.add("/web/login.html");
        patterns.add("/web/register.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/web/login.html");
        // 放行两个url
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");
        patterns.add("/products/**");


        //将自定义的拦截器的对象以参数的形式传入到registry中
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")//并且用addPathPatterns()方法添加拦截器黑名单(url)，“/**”表示全部拦截
                .excludePathPatterns(patterns);//excludePathPatterns()方法添加拦截器白名单，参数需要时List集合
    }
}
