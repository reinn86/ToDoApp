package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.logic.LoginLogic;
import model.util.ConnectDB;
import model.util.UserDAO;

/**
 * Servlet implementation class LoginPage
 */
@WebServlet("/login")
public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//ページ
	String loginPage = "/Login.jsp";
	String toDoServ = "./todo";
	//ロジック
	LoginLogic loginLogic = new LoginLogic();
	
	public LoginPage() {
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
