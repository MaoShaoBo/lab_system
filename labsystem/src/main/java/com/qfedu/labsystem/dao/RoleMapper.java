package com.qfedu.labsystem.dao;

import com.qfedu.labsystem.pojo.Role;
import com.qfedu.labsystem.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    public List<Role> getRoleList();

    void updateRolelist(Role role);

    void deleteRoleList(Integer id);

//    @Insert("insert into lab_system.roles (roleName) values (#{roleName})")
    void addRoleList(Role role);
}
