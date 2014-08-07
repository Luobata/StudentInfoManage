package com.luobata.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.luobata.dao.UserDao;
import com.luobata.model.User;
import com.luobata.util.DbUtil;
import com.luobata.util.StringUtil;


public class LoginServlet extends HttpServlet{

	DbUtil dbUtil=new DbUtil();
	UserDao userDao=new UserDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse respond)
			throws ServletException, IOException {
		this.doPost(request, respond);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse respond)
			throws ServletException, IOException {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)){
			request.setAttribute("error", "�û���������Ϊ��");
			request.getRequestDispatcher("index.jsp").forward(request, respond);
			return;
		}
		User user= new User(userName,password);
		Connection con=null;
		try {
			con=dbUtil.getCon();
			User currentUser=userDao.login(con, user);
			if(currentUser==null){
				request.setAttribute("error", "�û����������");
				//����������ת
				request.getRequestDispatcher("index.jsp").forward(request, respond);}
			else{
				//��ȡsession
				HttpSession session=request.getSession();
				session.setAttribute("currentUser", currentUser);
			   //�ͻ�����ת		
				respond.sendRedirect("main.jsp");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
