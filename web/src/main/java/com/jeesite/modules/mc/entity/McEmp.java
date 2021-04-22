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
 * 水电站流量Entity
 * @author mxbt
 * @version 2020-07-07
 */
@Table(name="mc_emp", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="hy_id", attrName="hyId", label="主键"),
		@Column(name="hy_name", attrName="hyName", label="水电站", queryType=QueryType.LIKE),
		@Column(name="hy_address", attrName="hyAddress", label="所在位置"),
		@Column(name="ecology_out_flow", attrName="ecologyOutFlow", label="生态流量 ", comment="生态流量 (m³/s)"),
		@Column(name="build_dondation", attrName="buildDondation", label="运行情况"),
		@Column(name="development_mode", attrName="developmentMode", label="开发方式"),
		@Column(name="emp_type", attrName="empType", label="类型"),
		@Column(name="in_flow", attrName="inFlow", label="引水流量 ", comment="引水流量 (m³/s)"),
		@Column(name="collection_time", attrName="collectionTime", label="时间"),
		@Column(name="areacode", attrName="areacode", label="区域代码"),
	}, orderBy="a.id DESC"
)
public class McEmp extends DataEntity<McEmp> {
	
	private static final long serialVersionUID = 1L;
	private String hyId;		// 主键
	private String hyName;		// 水电站
	private String hyAddress;		// 所在位置
	private String ecologyOutFlow;		// 生态流量 (m³/s)
	private String buildDondation;		// 运行情况
	private String developmentMode;		// 开发方式
	private String empType;		// 类型
	private String inFlow;		// 引水流量 (m³/s)
	private String collectionTime;		// 时间
	private String areacode;		// 区域代码
	
	public McEmp() {
		this(null);
	}

	public McEmp(String id){
		super(id);
	}
	
	public String getHyId() {
		return hyId;
	}

	public void setHyId(String hyId) {
		this.hyId = hyId;
	}
	
	@Length(min=0, max=255, message="水电站长度不能超过 255 个字符")
	public String getHyName() {
		return hyName;
	}

	public void setHyName(String hyName) {
		this.hyName = hyName;
	}
	
	public String getHyAddress() {
		return hyAddress;
	}

	public void setHyAddress(String hyAddress) {
		this.hyAddress = hyAddress;
	}
	
	public String getEcologyOutFlow() {
		return ecologyOutFlow;
	}

	public void setEcologyOutFlow(String ecologyOutFlow) {
		this.ecologyOutFlow = ecologyOutFlow;
	}
	
	@Length(min=0, max=255, message="运行情况长度不能超过 255 个字符")
	public String getBuildDondation() {
		return buildDondation;
	}

	public void setBuildDondation(String buildDondation) {
		this.buildDondation = buildDondation;
	}
	
	@Length(min=0, max=255, message="开发方式长度不能超过 255 个字符")
	public String getDevelopmentMode() {
		return developmentMode;
	}

	public void setDevelopmentMode(String developmentMode) {
		this.developmentMode = developmentMode;
	}
	
	@Length(min=0, max=255, message="类型长度不能超过 255 个字符")
	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}
	
	@Length(min=0, max=255, message="引水流量 长度不能超过 255 个字符")
	public String getInFlow() {
		return inFlow;
	}

	public void setInFlow(String inFlow) {
		this.inFlow = inFlow;
	}
	
	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
	
	@Length(min=0, max=255, message="区域代码长度不能超过 255 个字符")
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
}