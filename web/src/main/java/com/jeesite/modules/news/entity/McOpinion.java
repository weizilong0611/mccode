/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 意见举报Entity
 * @author zq
 * @version 2020-11-02
 */
@Table(name="mc_opinion", alias="a", columns={
		@Column(name="id", attrName="id", label="唯一值", isPK=true),
		@Column(name="opinion_type", attrName="opinionType", label="意见类型"),
		@Column(name="target_name", attrName="targetName", label="举报对象名称", comment="举报对象名称（农业局、环境局等）"),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="content", attrName="content", label="内容"),
		@Column(name="file_path", attrName="filePath", label="附件地址"),
		@Column(name="name", attrName="name", label="名称"),
		@Column(name="phone", attrName="phone", label="联系电话"),
		@Column(name="opinion_state", attrName="opinionState", label="状态", comment="状态(10未查看，20已审阅，30已回复)"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class McOpinion extends DataEntity<McOpinion> {
	
	private static final long serialVersionUID = 1L;
	private String opinionType;		// 意见类型
	private String targetName;		// 举报对象名称（农业局、环境局等）
	private String title;		// 标题
	private String content;		// 内容
	private String filePath;		// 附件地址
	private String name;		// 名称
	private String phone;		// 联系电话
	private String opinionState;		// 状态(10未查看，20已审阅，30已回复)
	
	public McOpinion() {
		this(null);
	}

	public McOpinion(String id){
		super(id);
	}
	
	@Length(min=0, max=20, message="意见类型长度不能超过 20 个字符")
	public String getOpinionType() {
		return opinionType;
	}

	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
	}
	
	@Length(min=0, max=100, message="举报对象名称长度不能超过 100 个字符")
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	@Length(min=0, max=100, message="标题长度不能超过 100 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=20, message="名称长度不能超过 20 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="联系电话长度不能超过 50 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getOpinionState() {
		return opinionState;
	}

	public void setOpinionState(String opinionState) {
		this.opinionState = opinionState;
	}
	
}