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
 * 植物图册Entity
 * @author 阿
 * @version 2020-09-16
 */
@Table(name="mc_plantgallery", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="kecn", attrName="kecn", label="科中文名"),
		@Column(name="keld", attrName="keld", label="科拉丁名"),
		@Column(name="kezm", attrName="kezm", label="科藏名"),
		@Column(name="shucn", attrName="shucn", label="属名"),
		@Column(name="shuld", attrName="shuld", label="属拉丁名"),
		@Column(name="shuzm", attrName="shuzm", label="属藏名"),
		@Column(name="zhongcn", attrName="zhongcn", label="种中文名"),
		@Column(name="zhongld", attrName="zhongld", label="种拉丁名"),
		@Column(name="zhongzm", attrName="zhongzm", label="种藏名"),
		@Column(name="infotxt", attrName="infotxt", label="内容介绍"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.id DESC"
)
public class McPlantgallery extends DataEntity<McPlantgallery> {
	
	private static final long serialVersionUID = 1L;
	private String kecn;		// 科中文名
	private String keld;		// 科拉丁名
	private String kezm;		// 科藏名
	private String shucn;		// 属名
	private String shuld;		// 属拉丁名
	private String shuzm;		// 属藏名
	private String zhongcn;		// 种中文名
	private String zhongld;		// 种拉丁名
	private String zhongzm;		// 种藏名
	private String infotxt;		// 内容介绍
	
	public McPlantgallery() {
		this(null);
	}

	public McPlantgallery(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="科中文名长度不能超过 255 个字符")
	public String getKecn() {
		return kecn;
	}

	public void setKecn(String kecn) {
		this.kecn = kecn;
	}
	
	@Length(min=0, max=255, message="科拉丁名长度不能超过 255 个字符")
	public String getKeld() {
		return keld;
	}

	public void setKeld(String keld) {
		this.keld = keld;
	}
	
	@Length(min=0, max=255, message="科藏名长度不能超过 255 个字符")
	public String getKezm() {
		return kezm;
	}

	public void setKezm(String kezm) {
		this.kezm = kezm;
	}
	
	@Length(min=0, max=255, message="属名长度不能超过 255 个字符")
	public String getShucn() {
		return shucn;
	}

	public void setShucn(String shucn) {
		this.shucn = shucn;
	}
	
	@Length(min=0, max=255, message="属拉丁名长度不能超过 255 个字符")
	public String getShuld() {
		return shuld;
	}

	public void setShuld(String shuld) {
		this.shuld = shuld;
	}
	
	@Length(min=0, max=255, message="属藏名长度不能超过 255 个字符")
	public String getShuzm() {
		return shuzm;
	}

	public void setShuzm(String shuzm) {
		this.shuzm = shuzm;
	}
	
	@Length(min=0, max=255, message="种中文名长度不能超过 255 个字符")
	public String getZhongcn() {
		return zhongcn;
	}

	public void setZhongcn(String zhongcn) {
		this.zhongcn = zhongcn;
	}
	
	@Length(min=0, max=255, message="种拉丁名长度不能超过 255 个字符")
	public String getZhongld() {
		return zhongld;
	}

	public void setZhongld(String zhongld) {
		this.zhongld = zhongld;
	}
	
	@Length(min=0, max=255, message="种藏名长度不能超过 255 个字符")
	public String getZhongzm() {
		return zhongzm;
	}

	public void setZhongzm(String zhongzm) {
		this.zhongzm = zhongzm;
	}
	
	@Length(min=0, max=4000, message="内容介绍长度不能超过 4000 个字符")
	public String getInfotxt() {
		return infotxt;
	}

	public void setInfotxt(String infotxt) {
		this.infotxt = infotxt;
	}
	
}