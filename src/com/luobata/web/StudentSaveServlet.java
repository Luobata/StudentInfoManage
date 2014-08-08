package com.luobata.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.luobata.dao.StudentDao;
import com.luobata.model.Student;
import com.luobata.model.PageBean;
import com.luobata.util.DateUtil;
import com.luobata.util.DbUtil;
import com.luobata.util.JsonUtil;
import com.luobata.util.ResponseUtil;
import com.luobata.util.StringUtil;

public class StudentSaveServlet extends HttpServlet{
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
		request.setCharacterEncoding("utf-8");
		String stuNo=request.getParameter("stuNo");
		String stuName=request.getParameter("stuName");
		String sex=request.getParameter("sex");
		String birthday=request.getParameter("birthday");
		String gradeId=request.getParameter("gradeId");
		String stuDesc=request.getParameter("stuDesc");
		String email=request.getParameter("email");
		String stuId=request.getParameter("stuId");
		
		Student student = null;
		try {
			student = new Student(stuNo, stuName, sex, DateUtil.formatString(birthday, "MM/dd/yyyy"),
					Integer.parseInt(gradeId), email, stuDesc);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(StringUtil.isNotEmpty(stuId)){
			student.setStuId(Integer.parseInt(stuId));
		}
		
		Connection con=null;
		try{
			con=dbutil.getCon();
			int saveNums;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(stuId)){
				 saveNums =studentDao.studentModify(con, student);
			}else{
				saveNums =studentDao.studentAdd(con, student);
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