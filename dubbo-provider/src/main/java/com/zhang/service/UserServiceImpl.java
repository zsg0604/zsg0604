package com.zhang.service;

import com.zhang.dao.UserDao;
import com.zhang.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public User queryUserName(String name) {
        return userDao.queryUserName(name);
    }

    @Override
    public List<Menu> queryTreeAll(Integer id) {

        return userDao.queryTreeAll(id);
    }

    @Override
    public HashMap<String, Object> queryUser(Integer page, Integer rows) {

        Integer total =userDao.queryUserCount();
        Map<String,Object> map=new HashMap<>();
        map.put("start",(page-1)*rows);
        map.put("end",rows);
        List<User> queryListUser=userDao.queryListUser(map);
        HashMap<String, Object> hashMap =new HashMap<String, Object>();
        hashMap.put("total",total);
        hashMap.put("rows",queryListUser);
        return hashMap;
    }

    @Override
    public List<Role> getRole(Integer id) {
        List<Integer> list =userDao.queryRoleById(id);
        List<Role> listTwo=userDao.queryRoleAll();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < listTwo.size(); j++) {
                if (list.get(i)==listTwo.get(j).getId()) {
                    listTwo.get(j).setChecked("true");
                }
            }
        }
        return listTwo;
    }

    @Override
    public void updateRole(Integer[] urids, Integer uid) {
        userDao.deleteUserRole(uid);
        for (int i = 0; i < urids.length; i++) {
            Userrole ur=new Userrole();
            ur.setRid(urids[i]);
            ur.setUid(uid);
            userDao.addUserRole(ur);
        }

    }

    @Override
    public HashMap<String, Object> queryRole(Integer page, Integer rows) {
        Map map=new HashMap();
        Integer total =userDao.queryRoleCount();
        int start=(page-1)*rows;
        int end=rows;
        map.put("start", start);
        map.put("end", end);
        List<Role> queryList=userDao.queryListRole(map);
        HashMap<String, Object> hashMap =new HashMap<String, Object>();
        hashMap.put("total",total);
        hashMap.put("rows",queryList);
        return hashMap;
    }

    @Override
    public List<Menu> getMenu(Integer id) {
        List<Integer> list=userDao.getMenu(id);
        List<Menu> listTwo=userDao.selectMenuAll();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < listTwo.size(); j++) {
                if (list.get(i)==listTwo.get(j).getId()) {
                    listTwo.get(j).setChecked("true");
                }
            }
        }
        return listTwo;
    }


    @Override
    public int updateMenu(Integer[] mids, Integer roleId) {
        int a=userDao.deleteMenu(roleId);
        for (int i = 0; i < mids.length; i++) {
            Rolemenu rm=new Rolemenu();
            rm.setMid(mids[i]);
            rm.setRid(roleId);
            a=userDao.addMenu(rm);
        }
        return 0;
    }

    @Override
    public HashMap<String, Object> queryLog(Integer page, Integer rows) {

        Query query = new Query();
        Criteria c =new Criteria();
        query.addCriteria(c);
        long total = mongoTemplate.count(query,LogBean.class, "log");
        query.skip((page-1)*rows);
        query.limit(rows);
        List<LogBean> queryList =mongoTemplate.find(query, LogBean.class, "log");
        HashMap<String, Object> hashMap =new HashMap<String, Object>();
        hashMap.put("total",total);
        hashMap.put("rows",queryList);
        return hashMap;

    }

    @Override
    public List<User> queryUserList() {
        return userDao.queryUserList();
    }

    @Override
    public void addUserAll(List<User> list) {

        for (int i = 0; i < list.size(); i++) {
            userDao.addUser(list.get(i));

        }
    }

    @Override
    public List<Map<String, Object>> queryBook() {

        return userDao.queryBook();
    }

    @Override
    public List<Map<String, Object>> queryBookLine() {
        return userDao.queryBookLine();
    }


}
