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
 * 藏药产业导航Entity
 * @author MXBT
 * @version 2020-08-12
 */
@Table(name="mc_medicine", alias="a", columns={
		@Column(name="aid", attrName="aid", label="aid", isPK=true),
		@Column(name="aimgtitle", attrName="aimgtitle", label="标题"),
		@Column(name="aimgname", attrName="aimgname", label="图片名"),
		@Column(name="aimgtxt", attrName="aimgtxt", label="内容"),
		@Column(name="areacode", attrName="areacode", label="行政区划代码"),
	}, orderBy="a.aid DESC"
)
public class McMedicine extends DataEntity<McMedicine> {
	
	private static final long serialVersionUID = 1L;
	private String aid;		// aid
	private String aimgtitle;		// 标题
	private String aimgname;		// 图片名
	private String aimgtxt;		// 内容
	private String areacode;		// 行政区划代码
	
	public McMedicine() {
		this(null);
	}

	public McMedicine(String id){
		super(id);
	}
	
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
	
	@Length(min=0, max=255, message="标题长度不能超过 255 个字符")
	public String getAimgtitle() {
		return aimgtitle;
	}

	public void setAimgtitle(String aimgtitle) {
		this.aimgtitle = aimgtitle;
	}
	
	@Length(min=0, max=255, message="图片名长度不能超过 255 个字符")
	public String getAimgname() {
		return aimgname;
	}

	public void setAimgname(String aimgname) {
		this.aimgname = aimgname;
	}
	
	@Length(min=0, max=4096, message="内容长度不能超过 4096 个字符")
	public String getAimgtxt() {
		return aimgtxt;
	}

	public void setAimgtxt(String aimgtxt) {
		this.aimgtxt = aimgtxt;
	}
	
	@Length(min=0, max=255, message="行政区划代码长度不能超过 255 个字符")
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
}