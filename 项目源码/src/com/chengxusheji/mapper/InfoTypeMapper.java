package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.InfoType;

public interface InfoTypeMapper {
	/*添加信息类别信息*/
	public void addInfoType(InfoType infoType) throws Exception;

	/*按照查询条件分页查询信息类别记录*/
	public ArrayList<InfoType> queryInfoType(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有信息类别记录*/
	public ArrayList<InfoType> queryInfoTypeList(@Param("where") String where) throws Exception;

	/*按照查询条件的信息类别记录数*/
	public int queryInfoTypeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条信息类别记录*/
	public InfoType getInfoType(int typeId) throws Exception;

	/*更新信息类别记录*/
	public void updateInfoType(InfoType infoType) throws Exception;

	/*删除信息类别记录*/
	public void deleteInfoType(int typeId) throws Exception;

}
