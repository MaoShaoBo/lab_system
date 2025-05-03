package com.qfedu.labsystem.service;

import com.qfedu.labsystem.pojo.Role;
import com.qfedu.labsystem.pojo.User;

import java.util.List;

public interface RoleService {
    public List<Role> getRoleList();

    void updateRolelist(Role role);

    void deleteRoleList(Integer id);

    void addRoleList(Role role);
}
