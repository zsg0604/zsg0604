package com.zhang.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable{
    private Integer id;

    private String name;

    private Integer pid;

    private String text;

    private String url;

    private String status;
    
    private String  checked;

    private String description;

    private List<Menu> children;
    

    public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}


    
    private Map<String, Object> attributes = new HashMap<String, Object>();
    

    public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}