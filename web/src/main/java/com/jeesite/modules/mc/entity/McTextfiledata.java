/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 数据浏览Entity
 * @author Mxbt
 * @version 2020-05-13
 */
@Table(name="mc_textfiledata", alias="", columns={
		@Column(name="id", attrName="id", label="数据", isPK=true),
		@Column(name="value", attrName="value", label="值"),
		@Column(name="rowtitle", attrName="rowtitle", label="行标题"),
		@Column(name="rowtime", attrName="rowtime", label="数据时间"),
		@Column(name="areacode", attrName="areacode", label="地区编码"),
		@Column(name="areaname", attrName="areaname", label="地名"),
		@Column(name="filepath", attrName="filepath", label="源文件地址"),
		@Column(name="filemd5", attrName="filemd5", label="文件Md5"),
		@Column(name="update_date", attrName="updateDate", label="更新日期", isQuery=false),
		@Column(name="datetype", attrName="datetype", label="数据类型"),
		@Column(name="ruleid", attrName="ruleid", label="规则Id"),
	}, orderBy="update_date DESC"
)
public class McTextfiledata extends DataEntity<McTextfiledata> {
	
	private static final long serialVersionUID = 1L;
	private String value;		// 值
	private String areacode;		// 地区编码
	private Date rowtime;		//时间
	private String areaname;
	private String rowtitle; 
	private String filepath;		// 源文件地址
	private String filemd5;		// 文件Md5
	private String datetype;		// 数据类型
	private String ruleid;		// 规则Id
	
	public McTextfiledata() {
		this(null);
	}

	public McTextfiledata(String id){
		super(id);
	}
	
	@Length(min=0, max=1024, message="值长度不能超过 1024 个字符")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@NotBlank(message="地区编码不能为空")
	@Length(min=0, max=32, message="地区编码长度不能超过 32 个字符")
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	@NotBlank(message="地区name不能为空")
	@Length(min=0, max=32, message="name长度不能超过 32 个字符")
	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	@NotBlank(message="源文件地址不能为空")
	@Length(min=0, max=255, message="源文件地址长度不能超过 255 个字符")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	@NotBlank(message="文件Md5不能为空")
	@Length(min=0, max=32, message="文件Md5长度不能超过 32 个字符")
	public String getFilemd5() {
		return filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}
	
	@Length(min=0, max=255, message="数据类型长度不能超过 255 个字符")
	public String getDatetype() {
		return datetype;
	}

	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}
	
	@Length(min=0, max=32, message="规则Id长度不能超过 32 个字符")
	public String getRuleid() {
		return ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Length(min=0, max=32, message="时间")
	public Date getRowtime() {
		return rowtime;
	}

	public void setRowtime(Date rowtime) {
		this.rowtime = rowtime;
	}
	
	@Length(min=0, max=255, message="行标题")
	public String getRowtitle() {
		return rowtitle;
	}

	public void setRowtitle(String rowtitle) {
		this.rowtitle = rowtitle;
	}

	
}