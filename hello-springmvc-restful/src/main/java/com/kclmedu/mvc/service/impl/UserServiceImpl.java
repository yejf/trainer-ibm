package com.kclmedu.mvc.service.impl;

import com.kclmedu.mvc.entity.User;
import com.kclmedu.mvc.mapper.IUserDao;
import com.kclmedu.mvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findById(Serializable id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(Serializable id) {
        userDao.delete(id);
    }

    @Override
    public User queryByAccountId(Serializable accountId) {
        return userDao.queryByAccountId(accountId);
    }

    @Override
    public User queryByUserName(String userName) {
        return userDao.queryByUserName(userName);
    }

    @Override
    public boolean isUserExists(String userName) {
        //todo ....
        return userName.contains("a");
    }
}
