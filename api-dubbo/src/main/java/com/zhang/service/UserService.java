package com.zhang.service;

import com.zhang.model.Menu;
import com.zhang.model.Role;
import com.zhang.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserName(String name);

    List<Menu> queryTreeAll(Integer id);

    HashMap<String, Object> queryUser(Integer page, Integer rows);

    List<Role> getRole(Integer id);

    void updateRole(Integer[] urids, Integer uid);

    HashMap<String, Object> queryRole(Integer page, Integer rows);

    List<Menu> getMenu(Integer id);

    int updateMenu(Integer[] mids, Integer roleId);

    HashMap<String, Object> queryLog(Integer page, Integer rows);

    List<User> queryUserList();

    void addUserAll(List<User> list);


    List<Map<String, Object>> queryBook();

    List<Map<String, Object>> queryBookLine();
}
