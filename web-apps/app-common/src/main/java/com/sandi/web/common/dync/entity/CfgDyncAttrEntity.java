package com.sandi.web.common.dync.entity;


import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;
import java.util.List;

public class CfgDyncAttrEntity extends BaseEntity {
	private Long minValue;//属性最小值

	private Long maxValue;//属性最大值

	private Long relatAttrId;//连带属性编号

	private String placeHolder;//提示语

	private String isInit;//是否初始化下拉框0不初始化1初始化

	private String initParam;//数据初始化参数

	private String fontClass;//字体样式

	private String iconClass;//图标样式

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	@Id
	private Long attrId;//属性编号

	private String attrName;//属性名称

	private String attrCode;//属性编码

	private String editType;//编辑类型	address	aipopup	combobox	datepicker	dropdownlist	label	multiselect	numerictextbox	ropdownlist	textarea	textbox

	private String editFormat;//编辑格式

	private String defaultValue;//默认值

	private String url;//打开页面地址

	private String urlParam;//打开页面参数

	private String resSrc;//属性数据源

	private String resMethod;//属性数据源方法

	private String resParam;//属性数据源参数

	private String dataText;//返回值text

	private String dataValue;//返回值value

	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	private String dataPath;//数据路径

	private Long regexpId;//正则表达式

	private Long rulesetId;//规则集编号

	public Long getMinValue() {return minValue;}

	public void setMinValue(Long minValue) {this.minValue = minValue;}

	public Long getMaxValue() {return maxValue;}

	public void setMaxValue(Long maxValue) {this.maxValue = maxValue;}

	public Long getRelatAttrId() {return relatAttrId;}

	public void setRelatAttrId(Long relatAttrId) {this.relatAttrId = relatAttrId;}

	public String getPlaceHolder() {return placeHolder;}

	public void setPlaceHolder(String placeHolder) {this.placeHolder = placeHolder;}

	public String getInitParam() {return initParam;}

	public void setInitParam(String initParam) {this.initParam = initParam;}

	public String getFontClass() {return fontClass;}

	public void setFontClass(String fontClass) {this.fontClass = fontClass;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public String getRemark() {return remark;}

	public void setRemark(String remark) {this.remark = remark;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Long getOpId() {return opId;}

	public void setOpId(Long opId) {this.opId = opId;}

	public Long getAttrId() {return attrId;}

	public void setAttrId(Long attrId) {this.attrId = attrId;}

	public String getAttrName() {return attrName;}

	public void setAttrName(String attrName) {this.attrName = attrName;}

	public String getAttrCode() {return attrCode;}

	public void setAttrCode(String attrCode) {this.attrCode = attrCode;}

	public String getEditType() {return editType;}

	public void setEditType(String editType) {this.editType = editType;}

	public String getEditFormat() {return editFormat;}

	public void setEditFormat(String editFormat) {this.editFormat = editFormat;}

	public String getDefaultValue() {return defaultValue;}

	public void setDefaultValue(String defaultValue) {this.defaultValue = defaultValue;}

	public String getUrl() {return url;}

	public void setUrl(String url) {this.url = url;}

	public String getUrlParam() {return urlParam;}

	public void setUrlParam(String urlParam) {this.urlParam = urlParam;}

	public String getResSrc() {return resSrc;}

	public void setResSrc(String resSrc) {this.resSrc = resSrc;}

	public String getResMethod() {return resMethod;}

	public void setResMethod(String resMethod) {this.resMethod = resMethod;}

	public String getResParam() {return resParam;}

	public void setResParam(String resParam) {this.resParam = resParam;}

	public String getDataText() {return dataText;}

	public void setDataText(String dataText) {this.dataText = dataText;}

	public String getDataValue() {return dataValue;}

	public void setDataValue(String dataValue) {this.dataValue = dataValue;}

	public Long getRegexpId() {return regexpId;}

	public void setRegexpId(Long regexpId) {this.regexpId = regexpId;}

	public Long getRulesetId() {return rulesetId;}

	public void setRulesetId(Long rulesetId) {this.rulesetId = rulesetId;}

	@Column(isColumn = false)
	private List<CfgDyncRuleEntity> cfgDyncRuleEntities;//规则集规则

	public List<CfgDyncRuleEntity> getCfgDyncRuleEntities() {
		return cfgDyncRuleEntities;
	}

	public void setCfgDyncRuleEntities(List<CfgDyncRuleEntity> cfgDyncRuleEntities) {
		this.cfgDyncRuleEntities = cfgDyncRuleEntities;
	}

	public CfgDyncRuleExpEntity getCfgDyncRuleExpEntity() {
		return cfgDyncRuleExpEntity;
	}

	public void setCfgDyncRuleExpEntity(CfgDyncRuleExpEntity cfgDyncRuleExpEntity) {
		this.cfgDyncRuleExpEntity = cfgDyncRuleExpEntity;
	}

	@Column(isColumn = false)

	private CfgDyncRuleExpEntity cfgDyncRuleExpEntity;//表达式
}