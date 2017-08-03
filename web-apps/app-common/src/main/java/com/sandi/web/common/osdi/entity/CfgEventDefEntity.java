package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

public class CfgEventDefEntity extends BaseEntity {
	@Id
	private String eventId;//null

	private Integer eventKind;//事件种类，1-js文件，2-js内容，3-JAVA类

	private String eventDesc;//null

	private String eventFile;//js文件路径

	private String eventFunc;//js文件中的方法

	private String eventContent;//null

	private String eventClazz;//事件处理类

	private Integer state;//null

	public String getEventId() {return eventId;}

	public void setEventId(String eventId) {this.eventId = eventId;}

	public Integer getEventKind() {return eventKind;}

	public void setEventKind(Integer eventKind) {this.eventKind = eventKind;}

	public String getEventDesc() {return eventDesc;}

	public void setEventDesc(String eventDesc) {this.eventDesc = eventDesc;}

	public String getEventFile() {return eventFile;}

	public void setEventFile(String eventFile) {this.eventFile = eventFile;}

	public String getEventFunc() {return eventFunc;}

	public void setEventFunc(String eventFunc) {this.eventFunc = eventFunc;}

	public String getEventContent() {return eventContent;}

	public void setEventContent(String eventContent) {this.eventContent = eventContent;}

	public String getEventClazz() {return eventClazz;}

	public void setEventClazz(String eventClazz) {this.eventClazz = eventClazz;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

}