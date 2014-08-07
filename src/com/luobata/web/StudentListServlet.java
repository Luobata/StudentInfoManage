package com.luobata.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.luobata.dao.StudentDao;
import com.luobata.model.Student;
import com.luobata.model.PageBean;
import com.luobata.util.DbUtil;
import com.luobata.util.JsonUtil;
import com.luobata.util.ResponseUtil;
import com.luobata.util.StringUtil;

public class StudentListServlet extends HttpServlet{
	DbUtil dbutil=new DbUtil();
	StudentDao studentDao=new StudentDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page=request.getParameter("page");
		String stuNo=request.getParameter("stuNo");
		String stuName=request.getParameter("stuName");
		String sex=request.getParameter("sex");
		String bbirthday=request.getParameter("bbirthday");
		String ebirthday=request.getParameter("ebirthday");
		String gradeId=request.getParameter("gradeId");
		String rows=request.getParameter("rows");
		
		Student student =new Student();
		if(stuNo!=null){
			student.setStuNo(stuNo);
			student.setStuName(stuName);
			student.setSex(sex);
			if(StringUtil.isNotEmpty(gradeId)){
				student.setGradeId(Integer.parseInt(gradeId));
			}
		}
		
		PageBean pageBean= new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbutil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(studentDao.studentList(con, pageBean,student,bbirthday,ebirthday));
			int total =studentDao.studentCount(con,student,bbirthday,ebirthday);
			result.put("rows", jsonArray);
			result.put("total",total);
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