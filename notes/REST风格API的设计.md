# REST风格API的设计

首先了解一下HTTP的请求方式

1.  GET            ->　获取数据，也就是对应 查询【R】
2.  POST         ->    提交数据， 也就是对应 创建 【C】
3.  DELETE      ->    删除数据，也就是对应  删除【D】
4.  PUT            ->   更新数据【全部】，也就是对应 更新【U】
5.  PATCH       ->   更新数据【局部】， 也就是对应 更新 【U】
6.  ....

再来看一下设计REST 风格API时需要注意的事项：

1. 在URI中不要使用动词，仅可能地全部使用名词
2. 在URI中尽可能使用复数，如：/users, /users/1
3.  如果有多级层的情况，可以把子级做为查询字符串【请求参数】来传递
4. 在URI中加入版本号， 如：/api/v1/users,    /api/v2/users
5. 当然，也要根据公司的具体业务情况，再具体分析

如：

| 请求方式 | URI                    | 描述                               |
| -------- | ---------------------- | ---------------------------------- |
| GET      | /users                 | 获取所有的用户信息                 |
| GET      | /users/{id}            | 获取指定ID的用户信息               |
| GET      | /addr/{province}?q=xxx | 根据省份以及过滤条件查询出地址信息 |
| POST     | /users                 | 插入用户信息                       |
| DELETE   | /users                 | 删除所有用户信息                   |
| DELETE   | /USERS/{id}            | 删除指定的用户                     |
| PUT      | /users/{id}            | 更新指定ID的用户所有信息【ID除外】 |
| PATCH    | /user/{id}             | 更新指定ID的用户部分信息           |



### 响应状态码

> 在HTTP协议中定义了5大类，分别是：
>
> * 1xx
> * 2xx       --->  服务端处理OK
>   * 200   代表OK
>   * 201   代表 CREATE
>   * 203    ...
>   * 204   ...
>   * 
> * 3xx       ->  服务端请求转移
> * 4xx       --> 访问的资源不存在
> * 5xx       -->  服务端出错了
>   * 500   
>   * 50？  ...

### Spring 框架提供的 响应体实例类 ResponseEntity<T> 

> 此类可以封装响应数据以及响应头和响应状态，并由spring mvc框架自动转换成 json格式后响应给客户端。
>
> 具体的代码操作请查看 项目案例

所涉及到的注解及API

1. 注解
   * @PathVariable 
   * @RequestBody
   * @RestController
2. API
   * ResponseEntity<T>
   * HttpStatus
   * HttpHeaders
   * UriComponentBuilder

注：以上类型 和注解 都可以在 API 中查看原义，或是通过阅读代码理解它的作用。