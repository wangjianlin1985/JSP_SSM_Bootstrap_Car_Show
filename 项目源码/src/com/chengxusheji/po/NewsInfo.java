package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsInfo {
    /*信息id*/
    private Integer newsId;
    public Integer getNewsId(){
        return newsId;
    }
    public void setNewsId(Integer newsId){
        this.newsId = newsId;
    }

    /*信息类别*/
    private InfoType infoTypeObj;
    public InfoType getInfoTypeObj() {
        return infoTypeObj;
    }
    public void setInfoTypeObj(InfoType infoTypeObj) {
        this.infoTypeObj = infoTypeObj;
    }

    /*信息标题*/
    @NotEmpty(message="信息标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*信息内容*/
    @NotEmpty(message="信息内容不能为空")
    private String newsContent;
    public String getNewsContent() {
        return newsContent;
    }
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    /*阅读次数*/
    @NotNull(message="必须输入阅读次数")
    private Integer readNum;
    public Integer getReadNum() {
        return readNum;
    }
    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonNewsInfo=new JSONObject(); 
		jsonNewsInfo.accumulate("newsId", this.getNewsId());
		jsonNewsInfo.accumulate("infoTypeObj", this.getInfoTypeObj().getTypeName());
		jsonNewsInfo.accumulate("infoTypeObjPri", this.getInfoTypeObj().getTypeId());
		jsonNewsInfo.accumulate("title", this.getTitle());
		jsonNewsInfo.accumulate("newsContent", this.getNewsContent());
		jsonNewsInfo.accumulate("readNum", this.getReadNum());
		jsonNewsInfo.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonNewsInfo;
    }}