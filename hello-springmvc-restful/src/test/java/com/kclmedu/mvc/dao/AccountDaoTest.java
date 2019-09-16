package com.kclmedu.mvc.dao;

import com.kclmedu.mvc.BaseTests;
import com.kclmedu.mvc.entity.Account;
import com.kclmedu.mvc.mapper.IAccountDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountDaoTest extends BaseTests {

    @Autowired
    private IAccountDao accountDao;

    @Test
    public void testFindAll() {
        List<Account> accounts = accountDao.findAll();
        //
        if(accounts != null) {
            accounts.forEach(System.out::println);
        }
    }
}
