package com.luobata.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.luobata.dao.GradeDao;
import com.luobata.model.Grade;
import com.luobata.model.PageBean;
import com.luobata.util.DbUtil;
import com.luobata.util.JsonUtil;
import com.luobata.util.ResponseUtil;
import com.luobata.util.StringUtil;

public class GradeSaveServlet extends HttpServlet{
	DbUtil dbutil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String gradeName=request.getParameter("gradeName");
		String gradeDesc=request.getParameter("gradeDesc");
		String id=request.getParameter("id");
		Grade grade=new Grade(gradeName,gradeDesc);
		if(StringUtil.isNotEmpty(id)){
			grade.setId(Integer.parseInt(id));
		}
		
		Connection con=null;
		try{
			con=dbutil.getCon();
			int saveNums;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				 saveNums =gradeDao.gradeMoify(con, grade);
			}else{
				saveNums =gradeDao.gradeAdd(con, grade);
			}
			
			if(saveNums>0){
				result.put("success","true");
			}
			else{
				//这个true用于实现技术，而非添加成功
				result.put("success","true");
				result.put("errorMsg", "编辑失败");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbutil.closeCon(con);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
}