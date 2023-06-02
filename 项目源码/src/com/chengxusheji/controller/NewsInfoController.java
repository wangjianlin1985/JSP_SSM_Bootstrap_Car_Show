package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.NewsInfoService;
import com.chengxusheji.po.NewsInfo;
import com.chengxusheji.service.InfoTypeService;
import com.chengxusheji.po.InfoType;

//NewsInfo管理控制层
@Controller
@RequestMapping("/NewsInfo")
public class NewsInfoController extends BaseController {

    /*业务层对象*/
    @Resource NewsInfoService newsInfoService;

    @Resource InfoTypeService infoTypeService;
	@InitBinder("infoTypeObj")
	public void initBinderinfoTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("infoTypeObj.");
	}
	@InitBinder("newsInfo")
	public void initBinderNewsInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("newsInfo.");
	}
	/*跳转到添加NewsInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new NewsInfo());
		/*查询所有的InfoType信息*/
		List<InfoType> infoTypeList = infoTypeService.queryAllInfoType();
		request.setAttribute("infoTypeList", infoTypeList);
		return "NewsInfo_add";
	}

	/*客户端ajax方式提交添加新闻信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated NewsInfo newsInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        newsInfoService.addNewsInfo(newsInfo);
        message = "新闻信息添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询新闻信息信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("infoTypeObj") InfoType infoTypeObj,String title,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		if(rows != 0)newsInfoService.setRows(rows);
		List<NewsInfo> newsInfoList = newsInfoService.queryNewsInfo(infoTypeObj, title, addTime, page);
	    /*计算总的页数和总的记录数*/
	    newsInfoService.queryTotalPageAndRecordNumber(infoTypeObj, title, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = newsInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = newsInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(NewsInfo newsInfo:newsInfoList) {
			JSONObject jsonNewsInfo = newsInfo.getJsonObject();
			jsonArray.put(jsonNewsInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询新闻信息信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<NewsInfo> newsInfoList = newsInfoService.queryAllNewsInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(NewsInfo newsInfo:newsInfoList) {
			JSONObject jsonNewsInfo = new JSONObject();
			jsonNewsInfo.accumulate("newsId", newsInfo.getNewsId());
			jsonNewsInfo.accumulate("title", newsInfo.getTitle());
			jsonArray.put(jsonNewsInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询新闻信息信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("infoTypeObj") InfoType infoTypeObj,String title,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		List<NewsInfo> newsInfoList = newsInfoService.queryNewsInfo(infoTypeObj, title, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    newsInfoService.queryTotalPageAndRecordNumber(infoTypeObj, title, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = newsInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = newsInfoService.getRecordNumber();
	    request.setAttribute("newsInfoList",  newsInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("infoTypeObj", infoTypeObj);
	    request.setAttribute("title", title);
	    request.setAttribute("addTime", addTime);
	    List<InfoType> infoTypeList = infoTypeService.queryAllInfoType();
	    request.setAttribute("infoTypeList", infoTypeList);
		return "NewsInfo/newsInfo_frontquery_result"; 
	}

     /*前台查询NewsInfo信息*/
	@RequestMapping(value="/{newsId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer newsId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键newsId获取NewsInfo对象*/
        NewsInfo newsInfo = newsInfoService.getNewsInfo(newsId);
        newsInfo.setReadNum(newsInfo.getReadNum() + 1);
        newsInfoService.updateNewsInfo(newsInfo);
        
        List<InfoType> infoTypeList = infoTypeService.queryAllInfoType();
        request.setAttribute("infoTypeList", infoTypeList);
        request.setAttribute("newsInfo",  newsInfo);
        return "NewsInfo/newsInfo_frontshow";
	}

	/*ajax方式显示新闻信息修改jsp视图页*/
	@RequestMapping(value="/{newsId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer newsId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键newsId获取NewsInfo对象*/
        NewsInfo newsInfo = newsInfoService.getNewsInfo(newsId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonNewsInfo = newsInfo.getJsonObject();
		out.println(jsonNewsInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新新闻信息信息*/
	@RequestMapping(value = "/{newsId}/update", method = RequestMethod.POST)
	public void update(@Validated NewsInfo newsInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			newsInfoService.updateNewsInfo(newsInfo);
			message = "新闻信息更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "新闻信息更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除新闻信息信息*/
	@RequestMapping(value="/{newsId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer newsId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  newsInfoService.deleteNewsInfo(newsId);
	            request.setAttribute("message", "新闻信息删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "新闻信息删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条新闻信息记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String newsIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = newsInfoService.deleteNewsInfos(newsIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出新闻信息信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("infoTypeObj") InfoType infoTypeObj,String title,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<NewsInfo> newsInfoList = newsInfoService.queryNewsInfo(infoTypeObj,title,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "NewsInfo信息记录"; 
        String[] headers = { "信息id","信息类别","信息标题","阅读次数","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<newsInfoList.size();i++) {
        	NewsInfo newsInfo = newsInfoList.get(i); 
        	dataset.add(new String[]{newsInfo.getNewsId() + "",newsInfo.getInfoTypeObj().getTypeName(),newsInfo.getTitle(),newsInfo.getReadNum() + "",newsInfo.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"NewsInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
