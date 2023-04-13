package app.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.security.HtmlFilter;
import domain.model.User;
import domain.repository.ConnectDB;
import domain.repository.UserDAO;
import domain.service.login.LoginLogic;

/**
 * Servlet implementation class LoginPage
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//ページ
	String loginPage = "/WEB-INF/views/login/Login.jsp";
	String toDoServ = "./todo";
	//ロジック
	LoginLogic loginLogic = new LoginLogic();
	
	public LoginController() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//通信
		HttpSession session = request.getSession();
		//ユーザー
		String userName = request.getParameter("name");
		
		//ページ設定
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; UTF-8");
		
		HtmlFilter htmlFilter = new HtmlFilter();
		userName = htmlFilter.inputHtmlFilter(userName);
		//ログイン処理
		if(loginLogic.excute(userName)) {
			User user = new User();
			ConnectDB connectDB = new ConnectDB();
			UserDAO userDAO = new UserDAO(connectDB.getConnection());
			
			//ユーザー情報設定
			user.setUserName(userName);
			//新しいユーザーか判定
			if(!userDAO.hasUserData(userName)) {
				userDAO.appendUserData(userName);
			}
			//ページ遷移
			userDAO.close();
			session.setAttribute("user", user);
			response.sendRedirect(toDoServ);
		}
		else {
			// TODO 注意画面の表示
			
			RequestDispatcher dispatch = request.getRequestDispatcher(loginPage);
			dispatch.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
