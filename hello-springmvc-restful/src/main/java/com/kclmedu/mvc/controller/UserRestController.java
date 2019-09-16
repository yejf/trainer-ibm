package com.kclmedu.mvc.controller;

import com.kclmedu.mvc.entity.User;
import com.kclmedu.mvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/****************
 * REST风格的控制器
 * @RestController = @Controller + @ResponseBody
 *
 * 本案例所开放的URI有：
 *  GET  /v1/api/users    获取所有用户信息
 *  GET  /v1/api/users/{id}   获取指定用户信息
 *  POST /v1/api/users     创建用户
 *  DELETE /v1/api/users    删除所有用户
 *  DELETE  /v1/api/users/{id}  删除指定id的用户
 *  PUT    /v1/api/users/{id}  更新指定ID的用户
 */
@RestController
@RequestMapping(value = "/v1/api")
public class UserRestController {

    @Autowired
    private IUserService userService;

    //---------- 查询所有用户的操作 -------------
    @RequestMapping(value = "/users",method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userService.findAll();
        //判断
        if(userList == null || userList.isEmpty()) {
            //告诉客户端，没有相关的数据
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //追加一个响应头
      /*  HttpHeaders headers = new HttpHeaders();
        headers.set("header-name","header-value");*/
        //
//        return new ResponseEntity<>(userList,headers, HttpStatus.OK);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        //调用业方法
        User user = userService.findById(id);
        //判断
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("客户端传输过来的用户信息是："+user);
        //判断用户名是否已经存在
        if(userService.isUserExists(user.getUserName())) {
            //
            System.out.printf("用户名：%s已经存在了\n", user.getUserName());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        //调用业务方法
        userService.save(user);

        //构建一个响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/api/users/{id}").buildAndExpand(user.getId()).toUri());
        //
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //----------- 更新用户 ---------------
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        System.out.printf("你要更新的用户对象，其ID是：%d\n", id);
        //
        System.out.printf("传过来的User对象信息有： "+user);
        //根据ID先查询出对象
        User currentUser = userService.findById(id);
        //判断此对象是否存在
        if(currentUser == null) {
            System.out.printf("id为%d 的用户不存在\n", id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        //修改对象信息
        currentUser.setRealName(user.getRealName());
        currentUser.setPassword(user.getPassword());
        currentUser.setStatus(user.getStatus());
        currentUser.setUserName(user.getUserName());

        //调用业务方法更新
        userService.update(currentUser);
        //返回
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    //----------- 删除单个对象 -------------------
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
        System.out.printf("要删除的用户ID为：%d\n", id);
        //
        User user = userService.findById(id);
        //
        if(user == null) {
            //说明此ID不存在
            System.out.printf("用户ID: %d不存在\n", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //
        userService.delete(id);
        System.out.println("删除成功...");
        //删除后返回 无内容
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //---------- 删除所有用户 ------------
   /* @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("删除所有用户信息");
        //调用业务方法
        userService.deleteAll();
        //返回
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}
