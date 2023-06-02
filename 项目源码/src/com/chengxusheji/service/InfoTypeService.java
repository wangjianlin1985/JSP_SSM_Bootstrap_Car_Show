package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.InfoType;

import com.chengxusheji.mapper.InfoTypeMapper;
@Service
public class InfoTypeService {

	@Resource InfoTypeMapper infoTypeMapper;
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

    /*添加信息类别记录*/
    public void addInfoType(InfoType infoType) throws Exception {
    	infoTypeMapper.addInfoType(infoType);
    }

    /*按照查询条件分页查询信息类别记录*/
    public ArrayList<InfoType> queryInfoType(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return infoTypeMapper.queryInfoType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<InfoType> queryInfoType() throws Exception  { 
     	String where = "where 1=1";
    	return infoTypeMapper.queryInfoTypeList(where);
    }

    /*查询所有信息类别记录*/
    public ArrayList<InfoType> queryAllInfoType()  throws Exception {
        return infoTypeMapper.queryInfoTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = infoTypeMapper.queryInfoTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取信息类别记录*/
    public InfoType getInfoType(int typeId) throws Exception  {
        InfoType infoType = infoTypeMapper.getInfoType(typeId);
        return infoType;
    }

    /*更新信息类别记录*/
    public void updateInfoType(InfoType infoType) throws Exception {
        infoTypeMapper.updateInfoType(infoType);
    }

    /*删除一条信息类别记录*/
    public void deleteInfoType (int typeId) throws Exception {
        infoTypeMapper.deleteInfoType(typeId);
    }

    /*删除多条信息类别信息*/
    public int deleteInfoTypes (String typeIds) throws Exception {
    	String _typeIds[] = typeIds.split(",");
    	for(String _typeId: _typeIds) {
    		infoTypeMapper.deleteInfoType(Integer.parseInt(_typeId));
    	}
    	return _typeIds.length;
    }
}
