package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**持久层单元测试类*/
@SpringBootTest //表示标注当前的类为一个测试类，从而不会随同项目一起打包发送。
@RunWith(SpringRunner.class) //表示启动这个单元测试类（否则单元测试类是不能运行的），需要传递一个参数，必须是SpringRunner的实例类型
public class UserMapperTests {

    // 标红的原因: idea有检测功能，接口是不能够直接创建Bean的，但是Mybatis会帮助我们通过动态代理来创建实现类
    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试类中出现的方法必须是单元测试方法
     * 单元测试方法特点: 1. 必须被@Test修饰
     *                2. 返回值类型必须是void
     *                3. 方法的参数列表不能指定任何类型
     *                4. 方法的访问修饰符必须是public
     * 满足以上特点的，单元测试方法就可以独立运行，不用启动整个项目，就可以进行单元测试，提高了代码运行效率
     */
    @Test
    public void insert(){
        //创建一个user对象添加进mysql数据库中，根据返回的行数判断插入是否成功
        User user = new User();
        user.setUsername("yujunxin");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }
    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("yujunxin");
        System.out.println(user);
    }

    @Test
    public void findByuid(){
        User result = userMapper.findByUid(5);
        System.out.println(result);
    }

    @Test
    public void updatePasswordByUid(){
        Integer result = userMapper.updatePasswordByUid(5, "123", "admin", new Date());
        System.out.println(result);
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(1);
        user.setPhone("12345678901");
        user.setEmail("123456@163.com");
        user.setGender(1);
        user.setModifiedUser("admin");
        user.setModifiedTime(new Date());
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatar(){
        Integer uid = 8;
        String avatar = new String("/upload/avatar/");
        String modifiedUser = new String("admin");
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, modifiedUser, new Date());
        System.out.println(rows);
    }
}
