package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        User user = new User();
        user.setName("ann");
        user.setPassword("123456");
        user.setStatus(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        //
        userRepository.save(user);
    }

    @Test
    public void testFindById() {
        Optional<User> user = userRepository.findById("4028b8816d823659016d823674a90000");
        //
        System.out.println(user.get());  //user.toString()
        //打印所有的地址：
        user.get().getAddressList().forEach(System.out::println);
    }

    @Test
    public void testFindTopByName() {
        List<User> userList = userRepository.findTopByName("jack");
        //
        userList.forEach(System.out::println);
    }

    @Test
    public void testFindByNameAndPwd() {
        User user = userRepository.findByNameAndPwd("jack","123456");
        //
        System.out.println(user);
    }

    @Test
    public void testFindByStatus() {
        List<User> userList = userRepository.findByStatus(1);
        //
        userList.forEach(System.out::println);
    }

    @Test
    public void testFindByStatus2() {
        List<User> userList = userRepository.findByStatus2(1);
        //
        userList.forEach(System.out::println);
    }

    @Test
    public void testFindByProvince() {
        List<User> userList = userRepository.findByProvince("江苏省");
        //
        if(userList != null) {
            userList.forEach(System.out::println);
        }
    }

    @Test
    public void testFindByProperties() {
        User u = new User();
        u.setName("jack");
        u.setPassword("123456");
        //
        User user = userRepository.findByProperties(u);
        //
        System.out.println(user);
    }
}
