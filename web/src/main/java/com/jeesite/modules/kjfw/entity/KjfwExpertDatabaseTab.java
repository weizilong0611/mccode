package com.jeesite.modules.kjfw.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 
 * @author tony
 *  2021-04-07
 */
@Table(name = "kjfw_expert_database_tab", alias = "a", columns = { @Column(name = "id", attrName = "id", label = "id", isPK = true),
		@Column(name = "name", attrName = "name", label = "name"),
		@Column(name = "working_place", attrName = "workingPlace", label = "working_place"),
		@Column(name = "job_title", attrName = "jobtitle", label = "job_title", queryType = QueryType.LIKE),
		@Column(name = "professional_name", attrName = "professionalName", label = "professional_name"),
		@Column(name = "operation_year", attrName = "operationYear", label = "operation_year"),
		@Column(name = "province", attrName = "province", label = "province"),
		@Column(name = "city", attrName = "city", label = "city"),
		@Column(name = "province_code", attrName = "provinceCode", label = "province_code"),
		@Column(name = "city_code", attrName = "cityCode", label = "city_code"),
		@Column(name = "level", attrName = "level", label = "level"),
		@Column(name = "create_date", attrName = "createDate", label = "create_date"),
		@Column(name = "update_date", attrName = "updateDate", label = "update_date"),
         }, orderBy = "a.create_date DESC")
public class KjfwExpertDatabaseTab extends DataEntity<KjfwExpertDatabaseTab>{
	
	private static final long serialVersionUID = 1L;
	private String name; // 专家姓名
	private String workingPlace ; // 工作地点
	private String jobTitle ; // 职务名称
	private String professionalName ; // 专家名称
	private String operationYear ; // 从业年限
	private String province  ; // 省名称
	private String city ; // 市名称
	private String provinceCode  ; // 省代码
	private String cityCode ; // 市代码
	private String level ; //专家级别
	private Date createDate;//创建时间
	private Date updateDate;//修改时间
	
	public KjfwExpertDatabaseTab() {
		this(null);
	}
	
	public KjfwExpertDatabaseTab(String id) {
		super(id);
	}
	
	@Length(min = 0, max = 64, message = "name长度不能超过64个字符")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Length(min = 0, max = 500, message = "working_place长度不能超过500个字符")
	public String getWorkingPlace() {
		return workingPlace;
	}
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}
	@Length(min = 0, max = 200, message = "job_title长度不能超过200个字符")
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	@Length(min = 0, max = 200, message = "professional_name长度不能超过200个字符")
	public String getProfessionalName() {
		return professionalName;
	}
	public void setProfessionalName(String professionalName) {
		this.professionalName = professionalName;
	}
	@Length(min = 0, max = 2, message = "operation_year长度不能超过2个字符")
	public String getOperationYear() {
		return operationYear;
	}
	public void setOperationYear(String operationYear) {
		this.operationYear = operationYear;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
	
}
