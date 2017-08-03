package com.sandi.web.common.bp.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgBpTemplateEntity extends BaseEntity {
    @Id
    private Long templateId;//模板编号，主键

    private String templateName;//模板名称

    private Integer templateType;//模板类型：0：txt文本文件，1：csv格式，2：excel

    private Integer priority;//处理优先级(值越大表示优先级越高)

    private String dealStartTime;//数据处理开始时间段(HHMMSS)

    private String dealEndTime;//数据处理结束时间段(HHMMSS)

    private Integer dealCount;//一次处理的数据条数(默认处理全部)

    private String dealClass;//数据业务处理类

    private Integer state;//状态:1有效0无效

    private String remark;//备注

    private Date createDate;//创建时间

    private Date doneDate;//操作时间

    private Long creator;//创建人

    private Long opId;//操作人

    private String templateUrl;//模板路径

    private Long fileTypeId;//文件类型ID，关联cfg_elec表中的file_type_id用来配置文件上传路径

    private Long headerLines;//文件头行数

    private String rowSplit;//行分隔符(换行符号，仅支持\\r、\\n、\\r\\n、<cr>，忽略大小写)</cr>

    private String colSplit;//列分隔符

    private String fileEncode;//文件编码方式

    private Long ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDealStartTime() {
        return dealStartTime;
    }

    public void setDealStartTime(String dealStartTime) {
        this.dealStartTime = dealStartTime;
    }

    public String getDealEndTime() {
        return dealEndTime;
    }

    public void setDealEndTime(String dealEndTime) {
        this.dealEndTime = dealEndTime;
    }

    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }

    public String getDealClass() {
        return dealClass;
    }

    public void setDealClass(String dealClass) {
        this.dealClass = dealClass;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public Long getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(Long fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public Long getHeaderLines() {
        return headerLines;
    }

    public void setHeaderLines(Long headerLines) {
        this.headerLines = headerLines;
    }

    public String getRowSplit() {
        return rowSplit;
    }

    public void setRowSplit(String rowSplit) {
        this.rowSplit = rowSplit;
    }

    public String getColSplit() {
        return colSplit;
    }

    public void setColSplit(String colSplit) {
        this.colSplit = colSplit;
    }

    public String getFileEncode() {
        return fileEncode;
    }

    public void setFileEncode(String fileEncode) {
        this.fileEncode = fileEncode;
    }

    public Long getExt1() {
        return ext1;
    }

    public void setExt1(Long ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }
}