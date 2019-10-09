package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.User;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = String.class)
public interface UserRepository {

    //这里的方法定义参考 CrudRepository 接口或者是 JpaRepository

    User save(User user);

    Optional<User> findById(String id);

    Iterable<User> findAll();

    void flush();

    User saveAndFlush(User user);

    //添加一些其它的方法，使用 @Query 注解来完成
    List<User> findTopByName(String name);

    @Query("from User u where u.name = ?1 and u.password = ?2")
    User findByNameAndPwd(String name, String pwd);

    @Query("select u from User u where u.status = :status")
    List<User> findByStatus(@Param("status") int status);

    @Query(value="select u.* from tbl_user u where u.status = ?",nativeQuery = true)
    List<User> findByStatus2(int status);

    //支持between and
    @Query("from User u where u.createDate between :start and :end")
    List<User> findByCreateDate(@Param("start") Date start, @Param("end") Date end);

    //支持in 操作
    @Query("from User u where u.name in :nameList")
    List<User> findByNames(@Param("nameList") List<String> names);

    //关联查询
    //1.多对一， 可以使用隐式关联

    //2. 一对多， 显示关联
    @Query("select u from User u join u.addressList a where a.province = :province")
    List<User> findByProvince(@Param("province") String province);

    //如果方法的参数是一个实体类型，则我们使用 :#{#xxx} 来引用这个对象的属性值为绑定参数
    @Query("from User u where u.name = :#{#user.name} and u.password = :#{#user.password}")
    User findByProperties(@Param("user") User user);


}
