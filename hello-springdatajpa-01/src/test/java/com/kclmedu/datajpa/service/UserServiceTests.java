package com.kclmedu.datajpa.service;

import com.kclmedu.datajpa.BaseTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTests extends BaseTests {

    @Autowired
    private UserService userService;

    @Test
    public void testGrantRole() {
        String userId = "4028b8816d823659016d823674a90000";
        String[] roleIds = {"4028b8816d826a9f016d826ac5f90000"};
        //
        userService.grantRoles(userId, roleIds);
    }
}
