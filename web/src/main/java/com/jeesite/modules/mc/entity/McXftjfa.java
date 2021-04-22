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
 * 生态修复推荐Entity
 * @author MXBT
 * @version 2020-09-17
 */
@Table(name="mc_xftjfa", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="cate", attrName="cate", label="分类"),
		@Column(name="title", attrName="title", label="标题", comment="标题（工程区名称）", queryType=QueryType.LIKE),
		@Column(name="txt", attrName="txt", label="内容"),
	}, orderBy="a.id DESC"
)
public class McXftjfa extends DataEntity<McXftjfa> {
	
	private static final long serialVersionUID = 1L;
	private String cate;		// 分类
	private String title;		// 标题（工程区名称）
	private String txt;		// 内容
	
	public McXftjfa() {
		this(null);
	}

	public McXftjfa(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="分类长度不能超过 255 个字符")
	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}
	
	@Length(min=0, max=255, message="标题长度不能超过 255 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=4000000, message="内容长度不能超过 4000000 个字符")
	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	
}