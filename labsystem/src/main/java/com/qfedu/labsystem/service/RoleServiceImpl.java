package com.qfedu.labsystem.service;

import com.qfedu.labsystem.dao.RoleMapper;
import com.qfedu.labsystem.pojo.Role;
import com.qfedu.labsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public void updateRolelist(Role role) {
        roleMapper.updateRolelist(role);
    }

    @Override
    public void deleteRoleList(Integer id) {
        roleMapper.deleteRoleList(id);
    }

    @Override
    public void addRoleList(Role role) {
        roleMapper.addRoleList(role);
    }
}
