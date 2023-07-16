package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.PrintStream;
import java.util.Date;
import java.util.UUID;

/** 用户模块业务层实现类*/
@Service  //把对应的实现类交给springboot管理，不加的话会报错:找不到bean对象
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 实际上就是去调用mapper层的方法,因此要先创建userMapper对象
     * @param user 用户的数据对象
     */
    @Override
    public void reg(User user) {
        // 通过user对象获得传递过来的username值
        String username = user.getUsername();
        // 调用findByUsername来判断注册的用户名是否已经存在于数据库中
        User result = userMapper.findByUsername(username);
        // 判断结果集是否不为null，否则抛出用户名被占用的异常
        if(result != null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        // 密码加密处理的实现，比较流行的算法:md5算法的形式
        // (串 + password + 串) -->  整体交给md5算法进行加密，连续加载3次
        // 上述方法中的串通常被称为 盐值salt，而盐值本身也是一个随机的字符串
        String oldPassword = user.getPassword();
        // 获取盐值(随机生成一个盐值)
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 补全user类的salt属性信息
        user.setSalt(salt);
        // 将密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(oldPassword,salt);
        //将加密后的密码重新补全到user对象中
        user.setPassword(md5Password);

        // 需要补全部分信息(isDelete,createdUser,createdTime,modifiedUser,modifiedTime)
        //补全数据:isDelete
        user.setIsDelete(0);
        //补全数据：4个日记信息字段
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 执行注册业务(说明if语句没有执行)
        Integer rows = userMapper.insert(user);
        // 判断结果集rows是否为1，否则抛出数据插入异常
        if(rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }

    /**定义一个md5的算法加密 */
    private String getMD5Password(String password, String salt){
        for(int i=0;i<3;i++){
            // md5加密算法方法的调用
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        // 返回加密后的密码
        return password;
    }

    /**
     * 根据传入的username字符串在数据库查询数据，未查到抛异常，如果查到了比较密码是否一致，不一致抛异常，
     * @param username 用户名字符串
     * @param password 用户密码字符串
     * @return 返回查询到的用户数据 如果没有返回null
     *
     */
    @Override
    public User login(String username, String password) {
        // 利用持久层的UserMapper对象根据username字段在数据库进行查询
        User result = userMapper.findByUsername(username);
        // 根据返回的result数据进行判断
        // 返回的result为空，则说明数据不存在，直接抛出异常
        if(result == null){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        // 如果result不为空，则比较查询到的用户密码与数据用户密码是否匹配
        // 1. 记录数据库存储的用户密码
        String oldPassword = result.getPassword();
        // 2. 将用户输入的密码进行md5算法转换
        String salt = result.getSalt();
        String md5Password = getMD5Password(password, salt);
//        System.out.println(oldPassword);
//        System.out.println(md5Password);
        // 3. 判断两者密码是否相同
        if(!oldPassword.equals(md5Password)){
            throw new PasswordNotMatchException("用户密码输入错误，请重新输入正确密码");
        }

        // 此外，还需要判断用户isDelete数据是否为1,为1表示为用户删除
        if(result.getIsDelete() == 1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        // 减少数据层与层之间的传输体量，提升系统的性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        // 将当前的用户数据返回，返回的数据是为了辅助其他页面的数据展示使用(uid,username,avatar)
        return user;
    }

    /**
     * 根据用户的uid来修改用户密码
     * @param uid 用户的uid
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param username 当前修改密码的用户名
     */
    @Override
    public void changePassword(Integer uid, String oldPassword, String newPassword, String username) {
        // 1. 根据用户的uid判断用户是否存在,若不存在抛出UsernameNotFound异常
        //直接调用mapper里的findByUid
        User result = userMapper.findByUid(uid);
        if(result == null){
            throw new UsernameNotFoundException("当前用户不存在");
        }

        // 2. 若用户存在判断当前用户isDelete属性是否为1
        if(result.getIsDelete()==1){
            throw new UsernameNotFoundException("当前用户不存在");
        }

        // 3. 判断旧密码是否正确
        String md5Password = getMD5Password(oldPassword, result.getSalt());
        if(!md5Password.equals(result.getPassword())){
            throw new PasswordNotMatchException("密码错误");
        }

        // 4.调用map层接口UpdateByUid,进行修改密码操作
        Integer rows = userMapper.updatePasswordByUid(uid,
                getMD5Password(newPassword, result.getSalt()),
                result.getUsername(),
                new Date());
        // 判断返回值rows是否为1，不为1抛出UpdatePasswordByUid异常
        if(rows!=1){
            throw new UpdateException("在数据更新时发生了未知异常");
        }

    }

    /**
     * 根据uid查询用户数据
     * @param uid 用户的uid
     * @return 返回页面所需要的用户数据信息
     */
    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        //判断查询结果的
        if(result==null || result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        // 减少数据传输量，只回传需要的数据
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    /**
     * 根据用户的uid修改用户数据
     * @param user 用户信息
     * @param uid 用户的uid
     * @param username 用户的名称
     */
    @Override
    public void changeInfo(User user, Integer uid, String username) {
        // 根据uid查询用户是否存在
        User result = userMapper.findByUid(uid);
        //判断查询结果,如果数据不存在抛异常
        if(result==null || result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //手动补全user缺失的信息，因为springboot只给user填充了表单中的数据，没有uid和username
        user.setUsername(username);
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        //调用map层的方法，将用户数据进行更新
        Integer rows = userMapper.updateInfoByUid(user);
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }

    }

    /**
     * 修改用户头像存储在服务器上的路径信息
     * @param uid 用户的uid
     * @param avatar 用户的头像存储路径
     * @param username 用户的username
     */
    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        // 根据uid判断用户数据是否存在或者isDelete是否为1
        User result = userMapper.findByUid(uid);
        //判断查询结果,如果数据不存在抛异常
        if(result==null || result.getIsDelete()==1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        // 如果用户数据存在的话直接调用userMapper的changeAvatarByUid
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        //判断返回rows信息是否为1
        if(rows!=1){
            throw new UpdateException("更新用户数据时产生未知的异常");
        }
    }
}
