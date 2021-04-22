/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.entity;

import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import org.hibernate.validator.constraints.Length;

/**
 * 地图信息表Entity
 * @author mxbt
 * @version 2020-07-27
 */
@Table(name="mc_mapinfo", alias="a", columns={
		@Column(name="tree_code", attrName="treeCode", label="节点编码", isPK=true),
		@Column(includeEntity=TreeEntity.class),
		@Column(name="tree_name", attrName="treeName", label="图层名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(name="menucode", attrName="menucode", label="菜单id"),
		@Column(name="url", attrName="url", label="地图/图层地址"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="mapaction", attrName="mapaction", label="地图操作"),
		@Column(name="note", attrName="note"),
		@Column(name="remarks", attrName="remarks", label="地图操作")
	}, orderBy="a.tree_sorts, a.tree_code"
)
public class McMapinfo extends TreeEntity<McMapinfo> {
	
	private static final long serialVersionUID = 1L;
	private String treeCode;		// 节点编码
	private String treeName;		// 图层名称
	private String menucode;		// 菜单id
	private String url;		// 地图/图层地址
	private String mapaction;		// 地图操作
	private String note;
	


	public McMapinfo() {
		this(null);
	}

	public McMapinfo(String id){
		super(id);
	}
	@Length(min=0, max=4000, message="菜单id长度不能超过 4000 个字符")
	@Override
	public String getRemarks() {
		// TODO Auto-generated method stub
		return super.getRemarks();
	}
	
	@Override
	public McMapinfo getParent() {
		return parent;
	}

	@Override
	public void setParent(McMapinfo parent) {
		this.parent = parent;
	}
	
	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	
	@NotBlank(message="图层名称不能为空")
	@Length(min=0, max=200, message="图层名称长度不能超过 200 个字符")
	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	
	@Length(min=0, max=255, message="菜单id长度不能超过 255 个字符")
	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}
	
	@Length(min=0, max=1024, message="地图/图层地址长度不能超过 1024 个字符")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMapaction() {
		return mapaction;
	}

	public void setMapaction(String mapaction) {
		this.mapaction = mapaction;
	}

	
	@Length(min=0, max=4000, message="地图/图层地址长度不能超过 4000 个字符")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}