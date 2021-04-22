/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 工作空间Entity
 * @author MXBT
 * @version 2020-03-04
 */
@Table(name="mc_workspace", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="filepath", attrName="filepath", label="工作空间文件路径"),
		@Column(name="iserverdat", attrName="iserverdat", label="可以储存一些iserver相关数据", queryType=QueryType.LIKE),
		@Column(name="iportaldat", attrName="iportaldat", label="可以储存一些iportal相关数据", queryType=QueryType.LIKE),
		@Column(name="remarks", attrName="remarks", label="备注", queryType=QueryType.LIKE),
		@Column(name="tag", attrName="tag", label="标签，使用分号分割", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class McWorkspace extends DataEntity<McWorkspace> {
	
	private static final long serialVersionUID = 1L;
	private String filepath;		// 工作空间文件路径
	private String iserverdat;		// 可以储存一些iserver相关数据
	private String iportaldat;		// 可以储存一些iportal相关数据
	private String tag;		// 标签，使用分号分割
	
	public McWorkspace() {
		this(null);
	}

	public McWorkspace(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="工作空间文件路径长度不能超过 255 个字符")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String getIserverdat() {
		return iserverdat;
	}

	public void setIserverdat(String iserverdat) {
		this.iserverdat = iserverdat;
	}
	
	public String getIportaldat() {
		return iportaldat;
	}

	public void setIportaldat(String iportaldat) {
		this.iportaldat = iportaldat;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}