package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.exception.ServiceException;
import com.cy.store.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.zip.DataFormatException;

/**业务层单元测试类*/
@SpringBootTest //表示标注当前的类为一个测试类，从而不会随同项目一起打包发送。
@RunWith(SpringRunner.class) //表示启动这个单元测试类（否则单元测试类是不能运行的），需要传递一个参数，必须是SpringRunner的实例类型
public class UserServiceTests {

    // 标红的原因: idea有检测功能，接口是不能够直接创建Bean的，但是Mybatis会帮助我们通过动态代理来创建实现类
    @Autowired
    private UserServiceImpl userService;
    /**
     * 单元测试类中出现的方法必须是单元测试方法
     * 单元测试方法特点: 1. 必须被@Test修饰
     *                2. 返回值类型必须是void
     *                3. 方法的参数列表不能指定任何类型
     *                4. 方法的访问修饰符必须是public
     * 满足以上特点的，单元测试方法就可以独立运行，不用启动整个项目，就可以进行单元测试，提高了代码运行效率
     */
    @Test
    public void reg(){
        //创建一个user对象添加进mysql数据库中，根据返回的行数判断插入是否成功
        try {
            User user = new User();
            user.setUsername("tim");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            // 获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            // 获取异常具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        try {
            User result= userService.login("Tom", "123456");
            System.out.println(result);
        } catch (Exception e) {
            // 获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            // 获取异常具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePassword(){
        try {
            Integer uid = 5;
            String oldPassword = "123";
            String newPassword = "321";
            String username = "admin";
            userService.changePassword(uid,oldPassword,newPassword,username);
        } catch (Exception e) {
            // 获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            // 获取异常具体描述信息
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void getByUid(){
        System.out.println(userService.getByUid(1));
    }

    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("12345678901");
        user.setEmail("123456@qq.com");
        user.setGender(0);
        userService.changeInfo(user,6,"admin");
    }

    @Test
    public void changAvatar(){
        userService.changeAvatar(8,"upload/yujunxin.jpg","admin");
    }
}
