package com.kclmedu.mvc.dao;

import com.kclmedu.mvc.BaseTests;
import com.kclmedu.mvc.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDaoTest extends BaseTests {

    @Autowired
    private IUserDao userDao;

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        //
        if(users != null) {
            users.forEach(System.out::println);
        }
    }
}
