package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.BaseTests;
import com.kclmedu.datajpa.entity.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleRepositoryTests extends BaseTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSave() {
        Role role = new Role();
        role.setName("管理员");
        role.setDescription("拥有一切权限");
        //
        roleRepository.save(role);
    }
}
