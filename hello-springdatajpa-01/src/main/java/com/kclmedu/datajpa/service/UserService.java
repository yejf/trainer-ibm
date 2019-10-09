package com.kclmedu.datajpa.service;

import com.kclmedu.datajpa.entity.Role;

import java.util.List;

/*********
 * 用户业务接口
 */
public interface UserService {

    /*****
     * 分配角色给用户,本质上是操作中间表
     * @param userId
     * @param roleIds
     */
    void grantRoles(String userId, String[] roleIds);

}
