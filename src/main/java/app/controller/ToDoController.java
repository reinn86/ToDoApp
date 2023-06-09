package app.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.security.HtmlFilter;
import domain.model.Task;
import domain.model.User;
import domain.repository.ConnectDB;
import domain.repository.TaskDAO;
import domain.service.mytodo.TaskInputLogic;
import domain.service.mytodo.TaskListHtml;

/**
 * Servlet implementation class ToDoPage
 */
@WebServlet("/todo")
public class ToDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TaskInputLogic taskInputLogic = new TaskInputLogic();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToDoController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//通信
		HttpSession session = request.getSession();
		Enumeration<String> parameterNames = request.getParameterNames();
		ConnectDB connectDB = new ConnectDB();
		TaskDAO taskDAO = new TaskDAO(connectDB.getConnection());
		
		//ユーザー
		User user = (User)session.getAttribute("user");
		//FIXME 書き方がきれいじゃない
		if(user == null) {
			String errorPage = "/WEB-INF/views/error.jsp";
			RequestDispatcher dispatch = request.getRequestDispatcher(errorPage);
			dispatch.forward(request, response);
		}
		List<Task> tasks = taskDAO.getTaskList(user.getUserName());
		HtmlFilter htmlFilter = new HtmlFilter();
		
		//ページ設定
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; UTF-8");
		//パラメーターの取得
		while(parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement().toString();
			System.out.println(paramName);

			switch(paramName) {
				case "APPEND" -> {
					String contents = htmlFilter.inputHtmlFilter(request.getParameter("contents"));
					String term = request.getParameter("term");
					
					if(taskInputLogic.excute(contents)) {
						//タスク追加
						Task task = new Task();
						
						task.setContents(contents);	
						task.setTerm(term);
						tasks.add(task);
						if(term.equals("")) {
							term = null;
						}
						
						taskDAO.appendTaskData(
							user.getUserName(),
							contents,
							term);	
					}
				}
				case "COMPLETE" -> {
					int index = Integer.parseInt(request.getParameter("index"));
					tasks.get(index).setComplete(true);
					taskDAO.completeTask(user.getUserName(), tasks.get(index).getId());
				}
				case "DELETE" -> {
					int index = Integer.parseInt(request.getParameter("index"));
					taskDAO.deleteTask(user.getUserName(),tasks.get(index).getId());
					tasks.remove(index);
				}
				case "MODIFICATION" -> {
					int index = Integer.parseInt(request.getParameter("index"));
					String contents = request.getParameter("MODIFICATION");
					taskDAO.modificationTask(contents, tasks.get(index).getId());
					tasks.get(index).setContents(contents);
				}
			}
		}
		//タスクをhtmlに出力する
		String taskListHtml = new TaskListHtml().createHtml(tasks);
		request.setAttribute("task",taskListHtml);
		//ページ切り替え
		String toDoPage = "/WEB-INF/views/mytodo/MyToDo.jsp";
		RequestDispatcher dispatch = request.getRequestDispatcher(toDoPage);
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
