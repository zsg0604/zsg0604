/** 
 * <pre>项目名称:ssm-easyui-wdd 
 * 文件名称:TreeBean.java 
 * 包名:com.jk.wdd.pojo 
 * 创建日期:2019年6月13日下午3:20:07 
 * Copyright (c) 2019, yuxy123@gmail.com All Rights Reserved.</pre> 
 */  
package com.zhang.model;

import java.util.List;

/** 
 * <pre>项目名称：ssm-easyui-wdd    
 * 类名称：TreeBean    
 * 类描述：    
 * 创建人：wdd   
 * 创建时间：2019年6月13日 下午3:20:07    
 * 修改人：wdd 
 * 修改时间：2019年6月13日 下午3:20:07    
 * 修改备注：       
 * @version </pre>    
 */
public class TreeBean {

	private Integer id;
	private String text;
	private List<TreeBean> children;//放子节点
	private Integer pid;
	private String url;
	private String state;//节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	private String iconCls;//显示的节点图标CSS类ID。
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeBean> getChildren() {
		return children;
	}
	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
