/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.geo.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 数据管理Entity
 * @author Mxbt
 * @version 2020-05-12
 */
@Table(name="mc_geofolder", alias="a", columns={
		@Column(name="tree_code", attrName="treeCode", label="节点编码", isPK=true),
		@Column(includeEntity=TreeEntity.class),
		@Column(name="tree_name", attrName="treeName", label="节点名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(includeEntity=DataEntity.class),
		@Column(name="file_type", attrName="fileType", label="文件类型"),
		@Column(name="file_regex", attrName="fileRegex", label="文件正则表达式"),
		@Column(name="file_rule", attrName="fileRule", label="文件规则", comment="文件规则(关联字典表_geo_folder_fileRule)"),
	}, orderBy="a.tree_sorts, a.tree_code"
)
public class McGeofolder extends TreeEntity<McGeofolder> {
	
	private static final long serialVersionUID = 1L;
	private String treeCode;		// 节点编码
	private String treeName;		// 节点名称
	private String fileType;		// 文件类型
	private String fileRegex;		// 文件正则表达式
	private String fileRule;		// 文件规则(关联字典表_geo_folder_fileRule)
	
	public McGeofolder() {
		this(null);
	}

	public McGeofolder(String id){
		super(id);
	}
	
	@Override
	public McGeofolder getParent() {
		return parent;
	}

	@Override
	public void setParent(McGeofolder parent) {
		this.parent = parent;
	}
	
	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	
	@NotBlank(message="节点名称不能为空")
	@Length(min=0, max=200, message="节点名称长度不能超过 200 个字符")
	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	
	@NotBlank(message="文件类型不能为空")
	@Length(min=0, max=255, message="文件类型长度不能超过 255 个字符")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=255, message="文件正则表达式长度不能超过 255 个字符")
	public String getFileRegex() {
		return fileRegex;
	}

	public void setFileRegex(String fileRegex) {
		this.fileRegex = fileRegex;
	}
	
	@NotBlank(message="文件规则不能为空")
	@Length(min=0, max=255, message="文件规则长度不能超过 255 个字符")
	public String getFileRule() {
		return fileRule;
	}

	public void setFileRule(String fileRule) {
		this.fileRule = fileRule;
	}
	
}