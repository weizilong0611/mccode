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
 * 天气数据Entity
 * @author MXBT
 * @version 2020-07-28
 */
@Table(name="mc_weather", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="tem2", attrName="tem2", label="tem2"),
		@Column(name="tem1", attrName="tem1", label="tem1"),
		@Column(name="windpower", attrName="windpower", label="windpower"),
		@Column(name="cityname", attrName="cityname", label="cityname"),
		@Column(name="statedetailed", attrName="statedetailed", label="statedetailed"),
		@Column(name="cityx", attrName="cityx", label="cityx"),
		@Column(name="cityy", attrName="cityy", label="cityy"),
		@Column(name="winddir", attrName="winddir", label="winddir"),
		@Column(name="state2", attrName="state2", label="state2"),
		@Column(name="state1", attrName="state1", label="state1"),
		@Column(name="windstate", attrName="windstate", label="windstate"),
		@Column(name="centername", attrName="centername", label="centername"),
		@Column(name="temnow", attrName="temnow", label="temnow"),
		@Column(name="url", attrName="url", label="url"),
		@Column(name="pyname", attrName="pyname", label="pyname"),
		@Column(name="humidity", attrName="humidity", label="humidity"),
		@Column(name="time", attrName="time", label="time"),
		@Column(name="fontcolor", attrName="fontcolor", label="fontcolor"),
	}, orderBy="a.id DESC"
)
public class McWeather extends DataEntity<McWeather> {
	
	private static final long serialVersionUID = 1L;
	private String tem2;		// tem2
	private String tem1;		// tem1
	private String windpower;		// windpower
	private String cityname;		// cityname
	private String statedetailed;		// statedetailed
	private String cityx;		// cityx
	private String cityy;		// cityy
	private String winddir;		// winddir
	private String state2;		// state2
	private String state1;		// state1
	private String windstate;		// windstate
	private String centername;		// centername
	private String temnow;		// temnow
	private String url;		// url
	private String pyname;		// pyname
	private String humidity;		// humidity
	private String time;		// time
	private String fontcolor;		// fontcolor
	
	public McWeather() {
		this(null);
	}

	public McWeather(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="tem2长度不能超过 255 个字符")
	public String getTem2() {
		return tem2;
	}

	public void setTem2(String tem2) {
		this.tem2 = tem2;
	}
	
	@Length(min=0, max=255, message="tem1长度不能超过 255 个字符")
	public String getTem1() {
		return tem1;
	}

	public void setTem1(String tem1) {
		this.tem1 = tem1;
	}
	
	@Length(min=0, max=255, message="windpower长度不能超过 255 个字符")
	public String getWindpower() {
		return windpower;
	}

	public void setWindpower(String windpower) {
		this.windpower = windpower;
	}
	
	@Length(min=0, max=255, message="cityname长度不能超过 255 个字符")
	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	
	@Length(min=0, max=255, message="statedetailed长度不能超过 255 个字符")
	public String getStatedetailed() {
		return statedetailed;
	}

	public void setStatedetailed(String statedetailed) {
		this.statedetailed = statedetailed;
	}
	
	@Length(min=0, max=255, message="cityx长度不能超过 255 个字符")
	public String getCityx() {
		return cityx;
	}

	public void setCityx(String cityx) {
		this.cityx = cityx;
	}
	
	@Length(min=0, max=255, message="cityy长度不能超过 255 个字符")
	public String getCityy() {
		return cityy;
	}

	public void setCityy(String cityy) {
		this.cityy = cityy;
	}
	
	@Length(min=0, max=255, message="winddir长度不能超过 255 个字符")
	public String getWinddir() {
		return winddir;
	}

	public void setWinddir(String winddir) {
		this.winddir = winddir;
	}
	
	@Length(min=0, max=255, message="state2长度不能超过 255 个字符")
	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}
	
	@Length(min=0, max=255, message="state1长度不能超过 255 个字符")
	public String getState1() {
		return state1;
	}

	public void setState1(String state1) {
		this.state1 = state1;
	}
	
	@Length(min=0, max=255, message="windstate长度不能超过 255 个字符")
	public String getWindstate() {
		return windstate;
	}

	public void setWindstate(String windstate) {
		this.windstate = windstate;
	}
	
	@Length(min=0, max=255, message="centername长度不能超过 255 个字符")
	public String getCentername() {
		return centername;
	}

	public void setCentername(String centername) {
		this.centername = centername;
	}
	
	@Length(min=0, max=255, message="temnow长度不能超过 255 个字符")
	public String getTemnow() {
		return temnow;
	}

	public void setTemnow(String temnow) {
		this.temnow = temnow;
	}
	
	@Length(min=0, max=255, message="url长度不能超过 255 个字符")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=255, message="pyname长度不能超过 255 个字符")
	public String getPyname() {
		return pyname;
	}

	public void setPyname(String pyname) {
		this.pyname = pyname;
	}
	
	@Length(min=0, max=255, message="humidity长度不能超过 255 个字符")
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	@Length(min=0, max=255, message="time长度不能超过 255 个字符")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Length(min=0, max=255, message="fontcolor长度不能超过 255 个字符")
	public String getFontcolor() {
		return fontcolor;
	}

	public void setFontcolor(String fontcolor) {
		this.fontcolor = fontcolor;
	}
	
}