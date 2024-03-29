﻿package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.InfoType;
import com.chengxusheji.po.NewsInfo;

import com.chengxusheji.mapper.NewsInfoMapper;
@Service
public class NewsInfoService {

	@Resource NewsInfoMapper newsInfoMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加新闻信息记录*/
    public void addNewsInfo(NewsInfo newsInfo) throws Exception {
    	newsInfoMapper.addNewsInfo(newsInfo);
    }

    /*按照查询条件分页查询新闻信息记录*/
    public ArrayList<NewsInfo> queryNewsInfo(InfoType infoTypeObj,String title,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != infoTypeObj && infoTypeObj.getTypeId()!= null && infoTypeObj.getTypeId()!= 0)  where += " and t_newsInfo.infoTypeObj=" + infoTypeObj.getTypeId();
    	if(!title.equals("")) where = where + " and t_newsInfo.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_newsInfo.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return newsInfoMapper.queryNewsInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<NewsInfo> queryNewsInfo(InfoType infoTypeObj,String title,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != infoTypeObj && infoTypeObj.getTypeId()!= null && infoTypeObj.getTypeId()!= 0)  where += " and t_newsInfo.infoTypeObj=" + infoTypeObj.getTypeId();
    	if(!title.equals("")) where = where + " and t_newsInfo.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_newsInfo.addTime like '%" + addTime + "%'";
    	return newsInfoMapper.queryNewsInfoList(where);
    }

    /*查询所有新闻信息记录*/
    public ArrayList<NewsInfo> queryAllNewsInfo()  throws Exception {
        return newsInfoMapper.queryNewsInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(InfoType infoTypeObj,String title,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != infoTypeObj && infoTypeObj.getTypeId()!= null && infoTypeObj.getTypeId()!= 0)  where += " and t_newsInfo.infoTypeObj=" + infoTypeObj.getTypeId();
    	if(!title.equals("")) where = where + " and t_newsInfo.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_newsInfo.addTime like '%" + addTime + "%'";
        recordNumber = newsInfoMapper.queryNewsInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取新闻信息记录*/
    public NewsInfo getNewsInfo(int newsId) throws Exception  {
        NewsInfo newsInfo = newsInfoMapper.getNewsInfo(newsId);
        return newsInfo;
    }

    /*更新新闻信息记录*/
    public void updateNewsInfo(NewsInfo newsInfo) throws Exception {
        newsInfoMapper.updateNewsInfo(newsInfo);
    }

    /*删除一条新闻信息记录*/
    public void deleteNewsInfo (int newsId) throws Exception {
        newsInfoMapper.deleteNewsInfo(newsId);
    }

    /*删除多条新闻信息信息*/
    public int deleteNewsInfos (String newsIds) throws Exception {
    	String _newsIds[] = newsIds.split(",");
    	for(String _newsId: _newsIds) {
    		newsInfoMapper.deleteNewsInfo(Integer.parseInt(_newsId));
    	}
    	return _newsIds.length;
    }
}
