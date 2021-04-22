/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.app.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * app_editionEntity
 * 
 * @author ZQ
 * @version 2020-09-09
 */
@Table(name = "app_edition", alias = "a", columns = { @Column(name = "id", attrName = "id", label = "id", isPK = true),
		@Column(name = "edition_id", attrName = "editionId", label = "edition_id"),
		@Column(name = "app_type", attrName = "appType", label = "app_type"),
		@Column(name = "edition_name", attrName = "editionName", label = "edition_name", queryType = QueryType.LIKE),
		@Column(name = "update_txt", attrName = "updateTxt", label = "update_txt"),
		@Column(name = "file_path", attrName = "filePath", label = "file_path"),
		@Column(name = "total_downloads", attrName = "totalDownloads", label = "total_downloads"),
		@Column(includeEntity = DataEntity.class), }, orderBy = "a.create_date DESC")
public class AppEdition extends DataEntity<AppEdition> {

	private static final long serialVersionUID = 1L;
	private String editionId; // edition_id
	private String appType; // 类型
	private String editionName; // edition_name
	private String updateTxt; // update_txt
	private String filePath; // file_path
	private Long totalDownloads; // total_downloads

	public AppEdition() {
		this(null);
	}

	public AppEdition(String id) {
		super(id);
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	@Length(min = 0, max = 10, message = "edition_id长度不能超过 10 个字符")
	public String getEditionId() {
		return editionId;
	}

	public void setEditionId(String editionId) {
		this.editionId = editionId;
	}

	@Length(min = 0, max = 64, message = "edition_name长度不能超过 64 个字符")
	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}

	@Length(min = 0, max = 500, message = "update_txt长度不能超过 500 个字符")
	public String getUpdateTxt() {
		return updateTxt;
	}

	public void setUpdateTxt(String updateTxt) {
		this.updateTxt = updateTxt;
	}

	@Length(min = 0, max = 200, message = "file_path长度不能超过 200 个字符")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getTotalDownloads() {
		return totalDownloads;
	}

	public void setTotalDownloads(Long totalDownloads) {
		this.totalDownloads = totalDownloads;
	}

}