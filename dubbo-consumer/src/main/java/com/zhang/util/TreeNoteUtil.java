/** 
 * <pre>项目名称:zsg 
 * 文件名称:TreeNoteUtil.java 
 * 包名:com.zhang.util 
 * 创建日期:2019年7月3日下午2:14:35 
 * Copyright (c) 2019, yuxy123@gmail.com All Rights Reserved.</pre> 
 */  
package com.zhang.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhang.model.Menu;
import com.zhang.model.Menu;

/** 
 * <pre>项目名称：zsg    
 * 类名称：TreeNoteUtil    
 * 类描述：    
 * 创建人：张松光 
 *
 * 励志语录:业精于勤荒于嬉 行成于思毁于随
 *
 * 创建时间：2019年7月3日 下午2:14:35    
 * 修改人：张松光  1005227857@qq.com
 * 修改时间：2019年7月3日 下午2:14:35    
 * 修改备注：       
 * @version </pre>    
 */
public class TreeNoteUtil {

	 public final static List<Menu> getFatherNode(List<Menu> treesList){
	        List<Menu> newTrees = new ArrayList<Menu>();
	        for (Menu mt : treesList) {
	            if (mt.getPid() ==null || "".equals(mt.getPid()) || mt.getPid()==0 ) {//如果pId为空，则该节点为父节点
	                //递归获取父节点下的子节点
	                mt.setChildren(getChildrenNode(mt.getId().toString(), treesList));
	                newTrees.add(mt);
	            }
	        }
	        return newTrees;
	    }
	    
	    /**
	     * 递归获取子节点下的子节点
	     * @param pId 父节点的ID
	     * @param treesList 所有菜单树集合
	     * @return
	     */
	    private final static List<Menu> getChildrenNode(String pId, List<Menu> treesList){
	        List<Menu> newTrees = new ArrayList<Menu>();
	        for (Menu mt : treesList) {
	            if (mt.getPid()==null || mt.getPid()==0) continue;
	            if(mt.getPid() == Integer.valueOf(pId) ){
	                //递归获取子节点下的子节点，即设置树控件中的children
	                mt.setChildren(getChildrenNode(mt.getId().toString(), treesList));
	                //设置树控件attributes属性的数据
	                Map<String, Object> map = new HashMap<String, Object>();
	                map.put("url", mt.getUrl());
	                mt.setAttributes(map);
	                newTrees.add(mt);
	            }
	        }
	        return newTrees;
	    }
	
}
