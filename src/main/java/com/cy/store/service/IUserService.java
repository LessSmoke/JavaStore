package com.cy.store.service;

import com.cy.store.entity.User;

/**用户模块业务层接口 */
public interface IUserService {

    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录方法
     * @param username 用户名字符串
     * @param password 用户密码字符串
     * @return 返回User对象实例
     */
    User login(String username,String password);

    /**
     * 修改用户密码方法
     * @param uid 用户的uid
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param username 当前修改密码的用户名
     */
    void changePassword(Integer uid,
                        String oldPassword,
                        String newPassword,
                        String username);

    /**
     * 根据用户的uid查询用户数据
     * @param uid 用户的uid
     * @return 返回查询到的用户数据
     */
    User getByUid(Integer uid);


    /**
     * 修改用户数据
     * @param user 用户信息
     * @param uid 用户的uid
     * @param username 用户的名称
     */
    void changeInfo(User user,Integer uid,String username);

    /**
     *
     * @param uid
     * @param avatar
     * @param username
     */
    void changeAvatar(Integer uid,String avatar,String username);
}
