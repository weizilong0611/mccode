/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 新闻Entity
 * 
 * @author zq
 * @version 2020-10-20
 */
@Table(name = "mc_news", alias = "a", columns = {
		@Column(name = "id", attrName = "id", label = "栏目id表唯一值", isPK = true),
		@Column(name = "column_name", attrName = "columnName", label = "栏目名称 ", comment = "栏目名称 (新闻、重点项目等)", isQuery = false),
		@Column(name = "column_title", attrName = "columnTitle", label = "栏目表题", queryType = QueryType.LIKE),
		@Column(name = "column_content", attrName = "columnContent", label = "内容"),
		@Column(name = "column_count", attrName = "columnCount", label = "阅读次数"),
		@Column(name = "column_top", attrName = "columnTop", label = "是否置顶"),
		@Column(name = "column_pic", attrName = "columnPic", label = "是否标头有图片"),
		@Column(name = "column_pic_path", attrName = "columnPicPath", label = "图片路径"),
		@Column(name = "column_play", attrName = "columnPlay", label = "是否轮播"),
		@Column(name = "column_app", attrName = "columnApp", label = "app显示"),
		@Column(name = "column_author", attrName = "columnAuthor", label = "作者"),
		@Column(name = "source", attrName = "source", label = "来源"),
		@Column(includeEntity = DataEntity.class), }, orderBy = "a.create_date DESC")
public class McNews extends DataEntity<McNews> {

	private static final long serialVersionUID = 1L;
	private String columnName; // 栏目名称 (新闻、重点项目等)
	private String columnTitle; // 栏目表题
	private String columnContent; // 内容
	private Long columnCount; // 阅读次数
	private String columnTop; // 是否置顶
	private String columnPic; // 是否标头有图片
	private String columnPicPath; // 图片路径
	private String columnPlay; // 是否轮播
	private String columnApp; // app显示
	private String columnAuthor; // 作者
	private String source; // 来源

	public McNews() {
		this(null);
	}

	public McNews(String id) {
		super(id);
	}

	@Length(min = 0, max = 20, message = "栏目名称 长度不能超过 20 个字符")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
		if (columnName != null && !columnName.equals("")) {
			sqlMap.getWhere().and("column_name", QueryType.IN, columnName.split(","));
		}
	}

	@NotBlank(message = "栏目表题不能为空")
	@Length(min = 0, max = 100, message = "栏目表题长度不能超过 100 个字符")
	public String getColumnTitle() {
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}

	@NotBlank(message = "内容不能为空")
	public String getColumnContent() {
		return columnContent;
	}

	public void setColumnContent(String columnContent) {
		this.columnContent = columnContent;
	}

	public Long getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(Long columnCount) {
		this.columnCount = columnCount;
	}

	@Length(min = 0, max = 1, message = "是否置顶长度不能超过 1 个字符")
	public String getColumnTop() {
		return columnTop;
	}

	public void setColumnTop(String columnTop) {
		this.columnTop = columnTop;
	}

	@Length(min = 0, max = 1, message = "是否标头有图片长度不能超过 1 个字符")
	public String getColumnPic() {
		return columnPic;
	}

	public void setColumnPic(String columnPic) {
		this.columnPic = columnPic;
	}

	public String getColumnPicPath() {
		return columnPicPath;
	}

	public void setColumnPicPath(String columnPicPath) {
		this.columnPicPath = columnPicPath;
	}

	@NotBlank(message = "是否轮播不能为空")
	@Length(min = 0, max = 1, message = "是否轮播长度不能超过 1 个字符")
	public String getColumnPlay() {
		return columnPlay;
	}

	public void setColumnPlay(String columnPlay) {
		this.columnPlay = columnPlay;
	}

	@NotBlank(message = "app显示不能为空")
	@Length(min = 0, max = 1, message = "app显示长度不能超过 1 个字符")
	public String getColumnApp() {
		return columnApp;
	}

	public void setColumnApp(String columnApp) {
		this.columnApp = columnApp;
	}

	@Length(min = 0, max = 20, message = "作者长度不能超过 20 个字符")
	public String getColumnAuthor() {
		return columnAuthor;
	}

	public void setColumnAuthor(String columnAuthor) {
		this.columnAuthor = columnAuthor;
	}

	@Length(min = 0, max = 300, message = "来源长度不能超过 300 个字符")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}