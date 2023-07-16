package com.cy.store.mapper;
import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/** 用户模块的持久层接口*/
public interface UserMapper {

    /**
     * 插入用户数据
     * @param user 用户数据，传入的是封装好的user实体类
     * @return 受影响的行数(增、删、改，都受影响的行数作为返回值，可以根据返回值来判断是否执行方法)
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户数据
     * @param username 传入的用户的username，该字段为unique字段
     * @return 如果找到返回用户的数据，如果没有找到用户 返回null值
     */
    User findByUsername(String username);

    /**
     * 根据用户的uid查询用户数据
     * @param uid 传入用户的uid，该字段为数据库字段主键
     * @return 如果找到返回用户的数据，如果没找到用户，返回null值
     */
    User findByUid(Integer uid);

    /**
     * 根据用户的uid来修改用户的密码
     * @param uid 用户的uid
     * @param password 用户要修改的新密码
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(Integer uid,
                           String password,
                           String modifiedUser,
                           Date modifiedTime);

    /**
     * 更新用户信息
     * @param user 用户的数据信息
     * @return 返回受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据用户的uid值来修改用户的头像
     * @param uid 用户的uid
     * @param avatar 用户的头像
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 返回受影响的行数
     */
    //如果要注入的参数名称与数据库表单的字段名字一定不一样可以用 @Param("uid") Integer uid，
    // 表示将参数列表中的uid值注入到数据库对应的uid字段下
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
