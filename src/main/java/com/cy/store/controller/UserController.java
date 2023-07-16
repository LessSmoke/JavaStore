package com.cy.store.controller;
import com.cy.store.controller.exception.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**表现层实体类，其实现依赖于业务层接口*/
@RestController //标签当前类交给springboot处理
@RequestMapping("users") // 定义该类请求路径
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;


    /**
     * 接受数据方式
     * 1. 请求处理方法的参数列表设置为POJO类型来接受前端数据
     *          Springboot会将前端的url地址中的参数名和pojo类的属性名称进行比较，如果这两个名称相同，则将参数直接注入POJO类的属性中传递
     * 2. 请求处理方法的参数列表设置为非POJO类型
     *          Springboot会直接将请求的参数名和方法的参数名直接进行比较，如果名称相同，则自动完成值的注入
     */
    @RequestMapping("reg") // 定义该方法的请求路径
    //@ResponseBody // 表示此方法的响应结果以Json格式进行数据响应给到前端页面很麻烦 可以直接在类前添加@RestController来替代Controller和ResponseBody
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(ok);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username, password);
        // 将查询拿到的data数据存入全局session对象中
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        //测试 获取session中绑定的数据
//        System.out.println(getUidFromSession(session));
//        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(ok,data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        //从session对象中获取用户的uuid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层changePassword来修改新密码
        userService.changePassword(uid,oldPassword,newPassword,username);

        return new JsonResult<Void>(ok);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        //从session对象中获取用户的uid
        Integer uid = getUidFromSession(session);

        //调用service层的getByUid方法获得查询到封装好的用户数据发送给前端
        User data = userService.getByUid(uid);
        return new JsonResult<User>(ok,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象中有4部分数据: username、phone、email、gender
        //底层service需要传递的数据缺少uid因此 需要从session中拿到uid数据封装到user中整体传递到底层的service方法
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //补齐后调用底层service的changInfo方法
        userService.changeInfo(user,uid,username);
        return new JsonResult<Void>(ok);
    }

    /**定义一个常量用于限制文件大小*/
    public static final Integer AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    /**定义一个文件类型列表，表示哪些文件可以被接收*/
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile是SpringMVC提供的一个接口，这个接口包装了获取文件类型的数据(任何类型的file都可以接收),springBoot整合了SpringMVC
     * 因此只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，然后springboot可以自动的将文件中的数据传递给服务器的这个
     * 参数
     *
     * 注解@RequestParam():表示请求中的参数，将请求中的参数注入到请求处理方法的某个参数上，如果名称一致，则可以使用@RequestParam() 进行强行的标记
     * 和映射
     *
     * @param session session对象
     * @param file SpringBoot整合的SpringMVC下的MultipartFile接口用于接收客户端文件
     * @return 返回文件在服务器存储地址
     */
    // 返回类型为字符串，即文件在服务器上的存储位置，传递给前端，便于后续头像展示
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){
        // 判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("上传文件为空");
        }
        //判断文件大小是否超出限制
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("上传文件容量超出限制");
        }
        //判断文件类型是否正确
        String contentType = file.getContentType();
//        System.out.println(contentType);
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("上传文件类型错误");
        }

        //上传文件在服务器上的目录结构: ../upload/文件.jpg
        // 查询upload的路径，可以根据session的getServletContext寻找
        String parent = session.getServletContext().getRealPath("upload");
        // 让一个File对象指向这个路径，进行File是否存在的判断来判断服务器是否存在upload这个文件
        File dir = new File(parent);
        if(!dir.exists()){ // 检测当前目录是否存在
            dir.mkdirs(); // 创建当前的目录
        }

        // 获取文件名称，UUID工具来生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
//        System.out.println("originalFilename"+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index); // 获取文件后缀
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        // 设定目标文件存储位置，以及规定文件存储名字，dest声明完之后是一个空文件
        File dest = new File(dir,filename);
        System.out.println(dest);
        //将参数file中的数据写入到dest中,MultipartFile封装了javaIO的api transferTo
        try {
            file.transferTo(dest); //将file文件中的数据写入到dest中
        } catch (FileStateException e){
            throw new FileSizeException("文件状态异常");
        }
        catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的路径只需要一个相对路径就可以 ../upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid,avatar,username);
        // 返回用户头像的路径给前端页面，将来用于用户头像的展示
        return new JsonResult<String>(ok,avatar);
        /**  表单中添加以下三个属性进行测试
         action="/users/change_avatar"
         method="post"
         enctype="multipart/form-data"*/
    }
}
