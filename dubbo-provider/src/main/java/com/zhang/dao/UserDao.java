package com.zhang.dao;

import com.zhang.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User queryUserName(String name);

    List<Menu> queryTreeAll(Integer id);

    Integer queryUserCount();

    List<User> queryListUser(Map<String, Object> map);

    List<Integer> queryRoleById(Integer id);

    @Select("select * from role")
    List<Role> queryRoleAll();

    @Delete("delete from userrole where uid=#{uid}")
    void deleteUserRole(Integer uid);

    @Insert("insert into userrole(uid,rid) values(#{uid},#{rid})")
    void addUserRole(Userrole ur);

    @Select("select count(*) from role")
    Integer queryRoleCount();

    @Select("select * from role limit #{start},#{end}")
    List<Role> queryListRole(Map map);


    @Select("select rm.mid from rolemenu rm where  rm.rid=#{id}")
    List<Integer> getMenu(Integer id);

    @Select("select * from menu")
    List<Menu> selectMenuAll();

    @Delete("delete from rolemenu where rid=#{roleId}")
    int deleteMenu(Integer roleId);

    @Insert("insert into rolemenu(rid,mid) values(#{rid},#{mid})")
    int addMenu(Rolemenu rm);

    @Select("select * from user")
    List<User> queryUserList();

    @Insert("insert into user(name,password,realname) values(#{name},#{password},#{realname})")
    void addUser(User user);

    @Select("SELECT COUNT(*) 数量,DATE_FORMAT(b.bdate,'%Y') 年份 FROM book b GROUP BY DATE_FORMAT(b.bdate,'%Y')")
    List<Map<String, Object>> queryBook();

    @Select("SELECT COUNT(*) 数量,DATE_FORMAT(b.bdate,'%M') 月份 FROM book b GROUP BY DATE_FORMAT(b.bdate,'%Y') ")
    List<Map<String, Object>> queryBookLine();
}
