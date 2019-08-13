package com.zhang.controller;

import com.zhang.log.LogRequire;
import com.zhang.model.Menu;
import com.zhang.model.Role;
import com.zhang.model.User;
import com.zhang.service.UserService;
import com.zhang.util.CheckImgUtil;
import com.zhang.util.ExportExcel;
import com.zhang.util.TreeNoteUtil;
import org.apache.poi.ss.usermodel.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("toLogin")
    public  String toLogin(){
        return "login";
    }


    @RequestMapping("gainCode")
    @ResponseBody
    public void gainCode(HttpServletRequest request, HttpServletResponse response){
        try {
            CheckImgUtil.checkImg(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @LogRequire(operationExplain="用户系统登录",operationFunction="登录",operationModel="登录模块")
    @RequestMapping("login")
    @ResponseBody
    public String loginUser(User user, String code, HttpServletRequest request) {
        String realCode=request.getSession().getAttribute("checkcode").toString();

        if(!realCode.toLowerCase().equals(code.toLowerCase())){
            return "codeError";
        }

        User loginUser=userService.queryUserName(user.getName());
        if (loginUser==null) {
            return "nameError";
        }

       /* String key = "lockuser"+loginUser.getName();
        if (redisTemplate.hasKey(key)) {
            String str = redisTemplate.opsForValue().get(key);
            int count =Integer.parseInt(str);
            if (count==3) {
                return "0";
            }
        }*/

        if (!loginUser.getPassword().equals(user.getPassword())) {
           /* if (!redisTemplate.hasKey(key)) {
                redisTemplate.opsForValue().set(key, "1");
                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
            }else {
                Long inc = redisTemplate.opsForValue().increment(key, 1);
                if (inc==3) {
                    redisTemplate.expire(key, 5, TimeUnit.MINUTES);
                }
            }*/
            return "pwError";
        }

       // redisTemplate.delete(key);
        request.getSession().setAttribute("u", loginUser);
        return "success";
    }

    @RequestMapping("toTree")
    public String toTree(){
        return "page";
    }

    @RequestMapping("getTreeAll")
    @ResponseBody
    public List<Menu> queryTreeAll(HttpServletRequest request){

        User user=(User) request.getSession().getAttribute("u");
        List<Menu> list =new ArrayList<Menu>();
        String key ="treeAll"+user.getId();
        if (redisTemplate.hasKey(key)) {
            list = (List<Menu>) redisTemplate.opsForValue().get(key);
            System.out.println("缓存");
        }else {
            list=userService.queryTreeAll(user.getId());
            list = TreeNoteUtil.getFatherNode(list);
            redisTemplate.opsForValue().set(key, list);
            redisTemplate.expire(key, 1, TimeUnit.MINUTES);
            System.out.println("数据库");
        }
        return list;
    }

    @RequestMapping("toUser")
    public String toUser(){
        return  "user";
    }

    @RequestMapping("queryUser")
    @ResponseBody
    public HashMap<String,Object> queryUser(Integer page,Integer rows){
        return userService.queryUser(page,rows);
    }

    @RequestMapping("getRole")
    @ResponseBody
    public List<Role> getRole(Integer id){
        return userService.getRole(id);
    }

    @RequestMapping("updateRole")
    @ResponseBody
    public void updateRole(Integer[] urids,Integer uid) {
        userService.updateRole(urids,uid);
    }


    @RequestMapping("toRole")
    public  String toRole(){
        return "role";
    }


    @RequestMapping("queryRole")
    @ResponseBody
    public HashMap<String,Object> queryRole(Integer page,Integer rows) {
        return userService.queryRole(page,rows);
    }

    @RequestMapping("getMenu")
    @ResponseBody
    public List<Menu> getMenu(Integer id){
        System.out.println(id);
        List<Menu> list= userService.getMenu(id);
        list = TreeNoteUtil.getFatherNode(list);
        return list;
    }

    @RequestMapping("updateMenu")
    @ResponseBody
    public String updateMenu(Integer[] mids,Integer roleId) {
        int i=userService.updateMenu(mids,roleId);
        if (i==0) {
            return "0";
        }
        return "1";
    }

    @RequestMapping("toLog")
    public String toLog(){
        return  "log";
    }


    @RequestMapping("queryLog")
    @ResponseBody
    public HashMap<String,Object> queryLog(Integer page,Integer rows){
        return userService.queryLog(page,rows);
    }




    //导出excel
    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response,String v) throws Exception {
        // System.out.println(v);
        //String[] rowName = v.split(",");
        //导出的excel的标题
        String title = "用户管理";
        //导出excel的列名
        String[] rowName = {"编号","名称","密码","真实姓名"};
        //导出的excel数据
        List<Object[]>  dataList = new ArrayList<Object[]>();
        //查询的数据库的书籍信息
        List<User> list=   userService.queryUserList();
        //循环书籍信息
        for (int i = 0; i < list.size(); i++) {
            Object[] obj =new Object[rowName.length];

            obj[0]=list.get(i).getId();
            obj[1]=list.get(i).getName();
            obj[2]=list.get(i).getPassword();
            obj[3]=list.get(i).getRealname();


            dataList.add(obj);
        }
        ExportExcel exportExcel =new ExportExcel(title,rowName,dataList,response);
        exportExcel.export();

    }


    @RequestMapping("importExcel")
    public String importExcel(MultipartFile file, HttpServletResponse response) throws Exception {
        //获得上传文件上传的类型
        String contentType = file.getContentType();
        //上传文件的名称
        String fileName = file.getOriginalFilename();
        //获得文件的后缀名
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件的新的路径
        //生成新的文件名称
        String filePath = "./src/main/resources/templates/fileupload/";
        //创建list集合接收excel中读取的数据
        List<User> list =new ArrayList<User>();
        ExportExcel.uploadFile(file.getBytes(), filePath, fileName);
        //通过文件忽的WorkBook
        FileInputStream fileInputStream = new FileInputStream(filePath+fileName);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        //通过workbook对象获得sheet页 有可能不止一个sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            //获得里面的每一个sheet对象
            Sheet sheetAt = workbook.getSheetAt(i);
            //通过sheet对象获得每一行
            for (int j = 3; j < sheetAt.getPhysicalNumberOfRows(); j++) {
                User user=new User();
                Row row = sheetAt.getRow(j);
                if (row.getCell(1)!=null&&!"".equals(row.getCell(1))) {
                    user.setName(this.getCellValue(row.getCell(1)));
                }
                if (row.getCell(2)!=null&&!"".equals(row.getCell(2))) {
                    user.setPassword(this.getCellValue(row.getCell(2)));
                }
                if (row.getCell(3)!=null&&!"".equals(row.getCell(3))) {
                    user.setRealname(this.getCellValue(row.getCell(3)));
                }

                list.add(user);
            }
        }
        userService.addUserAll(list);

        return "redirect:/user/toUser";

    }

    private static String getCellValue(Cell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }


        return value;
    }



    @RequestMapping("toBook")
    public String toBook(){
        return "bookData";
    }
    @RequestMapping("queryBook")
    @ResponseBody
    public List<Map<String,Object>> queryBook(){
        List<Map<String,Object>> listMap=userService.queryBook();

        List<Map<String,Object>> lm=new ArrayList<>();
        for (int i = 0; i < listMap.size(); i++) {
            Map<String,Object> m=new HashMap();
            m.put("y",Integer.parseInt(listMap.get(i).get("数量").toString()));
            m.put("name",listMap.get(i).get("年份")+"年");

            lm.add(m);
        }

        return lm;
    }

    @RequestMapping("toLine")
    public String toLine(){
        return "bookLine";
    }
    @RequestMapping("queryBookLine")
    @ResponseBody
    public List<Map<String,Object>> queryBookLine(){
        List<Map<String,Object>> listMap=userService.queryBookLine();
        List<Map<String,Object>> lm=new ArrayList<>();
        for (int i = 0; i < listMap.size() ; i++) {
            Map<String,Object> m=new HashMap();
            int[] arr=new int[4];
            arr[0]=Integer.parseInt(listMap.get(i).get("数量").toString());
            arr[1]=1;
            arr[2]=4;
            arr[3]=8;
            m.put("data",arr);
            m.put("name",listMap.get(i).get("月份"));

            lm.add(m);

        }
        return lm;
    }

    @RequestMapping("toColumn")
    public String toColumn(){
        return "bookColumn";
    }



}
