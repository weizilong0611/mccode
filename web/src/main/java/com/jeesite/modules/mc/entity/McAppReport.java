/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * app报告下载Entity
 * 
 * @author fidelzhang
 * @version 2020-08-21
 */
@Table(name = "mc_app_report", alias = "a", columns = {
		@Column(name = "id", attrName = "id", label = "编号", isPK = true),
		@Column(name = "title", attrName = "title", label = "标题", queryType = QueryType.LIKE),
		@Column(name = "areacode", attrName = "areacode", label = "行政区划代码"),
		@Column(name = "areaname", attrName = "areaname", label = "区域名称"), 
		@Column(name = "type", attrName = "type"),
		@Column(name = "reportDate", attrName = "reportDate"),
		@Column(name = "img", attrName = "img", label = "图片名"),
		@Column(name = "subtitle", attrName = "subtitle", label = "子标题"),
		@Column(name = "file", attrName = "file", label = "行政区划代码"),
		@Column(includeEntity = DataEntity.class), }, orderBy = "a.update_date DESC")
public class McAppReport extends DataEntity<McAppReport> {

	private static final long serialVersionUID = 1L;
	private String title; // 标题
	private String areacode; // 行政区划代码
	private String areaname;
	private String img; // 图片名
	private String type;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date reportDate; //
	private String subtitle; // 子标题
	private String file; // 行政区划代码

	public McAppReport() {
		this(null);
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public McAppReport(String id) {
		super(id);
	}
	
	@NotBlank(message="标题不能为空")
	@Length(min=0, max=255, message="标题长度不能超过 255 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="行政区划代码长度不能超过 255 个字符")
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	@Length(min=0, max=255, message="图片名长度不能超过 255 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@NotBlank(message="子标题不能为空")
	@Length(min=0, max=4096, message="子标题长度不能超过 4096 个字符")
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	@Length(min=0, max=255, message="行政区划代码长度不能超过 255 个字符")
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}