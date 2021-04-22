/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.cas.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.Extend;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * js_sys_userEntity
 * @author 梦溪笔檀
 * @version 2019-03-04
 */
@Table(name="${_prefix}sys_user", alias="a", columns={
		@Column(name="user_code", attrName="userCode", label="user_code", isPK=true),
		@Column(name="login_code", attrName="loginCode", label="登录名"),
		@Column(name="user_name", attrName="userName", label="用户名", queryType=QueryType.LIKE),
		@Column(name="password", attrName="password", label="密码"),
		@Column(name="email", attrName="email", label="邮箱地址"),
		@Column(name="mobile", attrName="mobile", label="手机号码"),
		@Column(name="phone", attrName="phone", label="电话号码"),
		@Column(name="sex", attrName="sex", label="性别"),
		@Column(name="avatar", attrName="avatar", label="头像"),
		@Column(name="sign", attrName="sign", label="签名"),
		@Column(name="wx_openid", attrName="wxOpenid", label="wx_openid"),
		@Column(name="mobile_imei", attrName="mobileImei", label="mobile_imei"),
		@Column(name="user_type", attrName="userType", label="user_type"),
		@Column(name="ref_code", attrName="refCode", label="ref_code"),
		@Column(name="ref_name", attrName="refName", label="ref_name", queryType=QueryType.LIKE),
		@Column(name="mgr_type", attrName="mgrType", label="mgr_type"),
		@Column(name="pwd_security_level", attrName="pwdSecurityLevel", label="pwd_security_level"),
		@Column(name="pwd_update_date", attrName="pwdUpdateDate", label="pwd_update_date"),
		@Column(name="pwd_update_record", attrName="pwdUpdateRecord", label="pwd_update_record"),
		@Column(name="pwd_question", attrName="pwdQuestion", label="pwd_question"),
		@Column(name="pwd_question_answer", attrName="pwdQuestionAnswer", label="pwd_question_answer"),
		@Column(name="pwd_question_2", attrName="pwdQuestion2", label="pwd_question_2"),
		@Column(name="pwd_question_answer_2", attrName="pwdQuestionAnswer2", label="pwd_question_answer_2"),
		@Column(name="pwd_question_3", attrName="pwdQuestion3", label="pwd_question_3"),
		@Column(name="pwd_question_answer_3", attrName="pwdQuestionAnswer3", label="pwd_question_answer_3"),
		@Column(name="pwd_quest_update_date", attrName="pwdQuestUpdateDate", label="pwd_quest_update_date"),
		@Column(name="last_login_ip", attrName="lastLoginIp", label="last_login_ip"),
		@Column(name="last_login_date", attrName="lastLoginDate", label="last_login_date"),
		@Column(name="freeze_date", attrName="freezeDate", label="freeze_date"),
		@Column(name="freeze_cause", attrName="freezeCause", label="freeze_cause"),
		@Column(name="user_weight", attrName="userWeight", label="user_weight"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(includeEntity=Extend.class, attrName="extend"),
	}, orderBy="a.update_date DESC"
)
public class CasUser extends DataEntity<CasUser> {
	
	private static final long serialVersionUID = 1L;
	private String userCode;		// user_code
	private String loginCode;		// 登录名
	private String userName;		// 用户名
	private String password;		// 密码
	private String email;		// 邮箱地址
	private String mobile;		// 手机号码
	private String phone;		// 电话号码
	private String sex;		// 性别
	private String avatar;		// 头像
	private String sign;		// 签名
	private String wxOpenid;		// wx_openid
	private String mobileImei;		// mobile_imei
	private String userType;		// user_type
	private String refCode;		// ref_code
	private String refName;		// ref_name
	private String mgrType;		// mgr_type
	private Integer pwdSecurityLevel;		// pwd_security_level
	private Date pwdUpdateDate;		// pwd_update_date
	private String pwdUpdateRecord;		// pwd_update_record
	private String pwdQuestion;		// pwd_question
	private String pwdQuestionAnswer;		// pwd_question_answer
	private String pwdQuestion2;		// pwd_question_2
	private String pwdQuestionAnswer2;		// pwd_question_answer_2
	private String pwdQuestion3;		// pwd_question_3
	private String pwdQuestionAnswer3;		// pwd_question_answer_3
	private Date pwdQuestUpdateDate;		// pwd_quest_update_date
	private String lastLoginIp;		// last_login_ip
	private Date lastLoginDate;		// last_login_date
	private Date freezeDate;		// freeze_date
	private String freezeCause;		// freeze_cause
	private Integer userWeight;		// user_weight
	private Extend extend;		// 扩展字段
	
	public CasUser() {
		this(null);
	}

	public CasUser(String id){
		super(id);
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@NotBlank(message="登录名不能为空")
	@Length(min=0, max=100, message="登录名长度不能超过 100 个字符")
	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	
	@NotBlank(message="用户名不能为空")
	@Length(min=0, max=100, message="用户名长度不能超过 100 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@NotBlank(message="密码不能为空")
	@Length(min=0, max=100, message="密码长度不能超过 100 个字符")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=300, message="邮箱地址长度不能超过 300 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=100, message="手机号码长度不能超过 100 个字符")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=100, message="电话号码长度不能超过 100 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=1, message="性别长度不能超过 1 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=1000, message="头像长度不能超过 1000 个字符")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Length(min=0, max=200, message="签名长度不能超过 200 个字符")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Length(min=0, max=100, message="wx_openid长度不能超过 100 个字符")
	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}
	
	@Length(min=0, max=100, message="mobile_imei长度不能超过 100 个字符")
	public String getMobileImei() {
		return mobileImei;
	}

	public void setMobileImei(String mobileImei) {
		this.mobileImei = mobileImei;
	}
	
	@NotBlank(message="user_type不能为空")
	@Length(min=0, max=16, message="user_type长度不能超过 16 个字符")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Length(min=0, max=64, message="ref_code长度不能超过 64 个字符")
	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	
	@Length(min=0, max=100, message="ref_name长度不能超过 100 个字符")
	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}
	
	@NotBlank(message="mgr_type不能为空")
	@Length(min=0, max=1, message="mgr_type长度不能超过 1 个字符")
	public String getMgrType() {
		return mgrType;
	}

	public void setMgrType(String mgrType) {
		this.mgrType = mgrType;
	}
	
	public Integer getPwdSecurityLevel() {
		return pwdSecurityLevel;
	}

	public void setPwdSecurityLevel(Integer pwdSecurityLevel) {
		this.pwdSecurityLevel = pwdSecurityLevel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPwdUpdateDate() {
		return pwdUpdateDate;
	}

	public void setPwdUpdateDate(Date pwdUpdateDate) {
		this.pwdUpdateDate = pwdUpdateDate;
	}
	
	@Length(min=0, max=1000, message="pwd_update_record长度不能超过 1000 个字符")
	public String getPwdUpdateRecord() {
		return pwdUpdateRecord;
	}

	public void setPwdUpdateRecord(String pwdUpdateRecord) {
		this.pwdUpdateRecord = pwdUpdateRecord;
	}
	
	@Length(min=0, max=200, message="pwd_question长度不能超过 200 个字符")
	public String getPwdQuestion() {
		return pwdQuestion;
	}

	public void setPwdQuestion(String pwdQuestion) {
		this.pwdQuestion = pwdQuestion;
	}
	
	@Length(min=0, max=200, message="pwd_question_answer长度不能超过 200 个字符")
	public String getPwdQuestionAnswer() {
		return pwdQuestionAnswer;
	}

	public void setPwdQuestionAnswer(String pwdQuestionAnswer) {
		this.pwdQuestionAnswer = pwdQuestionAnswer;
	}
	
	@Length(min=0, max=200, message="pwd_question_2长度不能超过 200 个字符")
	public String getPwdQuestion2() {
		return pwdQuestion2;
	}

	public void setPwdQuestion2(String pwdQuestion2) {
		this.pwdQuestion2 = pwdQuestion2;
	}
	
	@Length(min=0, max=200, message="pwd_question_answer_2长度不能超过 200 个字符")
	public String getPwdQuestionAnswer2() {
		return pwdQuestionAnswer2;
	}

	public void setPwdQuestionAnswer2(String pwdQuestionAnswer2) {
		this.pwdQuestionAnswer2 = pwdQuestionAnswer2;
	}
	
	@Length(min=0, max=200, message="pwd_question_3长度不能超过 200 个字符")
	public String getPwdQuestion3() {
		return pwdQuestion3;
	}

	public void setPwdQuestion3(String pwdQuestion3) {
		this.pwdQuestion3 = pwdQuestion3;
	}
	
	@Length(min=0, max=200, message="pwd_question_answer_3长度不能超过 200 个字符")
	public String getPwdQuestionAnswer3() {
		return pwdQuestionAnswer3;
	}

	public void setPwdQuestionAnswer3(String pwdQuestionAnswer3) {
		this.pwdQuestionAnswer3 = pwdQuestionAnswer3;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPwdQuestUpdateDate() {
		return pwdQuestUpdateDate;
	}

	public void setPwdQuestUpdateDate(Date pwdQuestUpdateDate) {
		this.pwdQuestUpdateDate = pwdQuestUpdateDate;
	}
	
	@Length(min=0, max=100, message="last_login_ip长度不能超过 100 个字符")
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}
	
	@Length(min=0, max=200, message="freeze_cause长度不能超过 200 个字符")
	public String getFreezeCause() {
		return freezeCause;
	}

	public void setFreezeCause(String freezeCause) {
		this.freezeCause = freezeCause;
	}
	
	public Integer getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(Integer userWeight) {
		this.userWeight = userWeight;
	}
	
	public Extend getExtend() {
		return extend;
	}

	public void setExtend(Extend extend) {
		this.extend = extend;
	}
	
}