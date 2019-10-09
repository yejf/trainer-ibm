package com.kclmedu.datajpa.service.impl;

import com.kclmedu.datajpa.dao.RoleRepository;
import com.kclmedu.datajpa.dao.UserRepository;
import com.kclmedu.datajpa.entity.Role;
import com.kclmedu.datajpa.entity.User;
import com.kclmedu.datajpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void grantRoles(String userId, String[] roleIds) {
        //1.根据userId查询出用户
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        //2.查询出已有的角色
        Set<Role> roles = user.getRoleSet(); //内存操作
        if (roles != null && roles.size() > 0) {
            //解除之前的关系
            user.setRoleSet(null); //内存操作
        }
        //3.重新注册新的角色
        for (String roleId : roleIds) {
            Role role = roleRepository.findById(roleId).get();
            //添加到 user中去
            user.addRole(role); //在内存中注册对象关系
        }
        //4.刷新
        user = userRepository.saveAndFlush(user);
    }

}
