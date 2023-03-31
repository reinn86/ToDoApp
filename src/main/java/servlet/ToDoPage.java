package servlet;

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

import model.bean.Task;
import model.bean.User;
import model.logic.TaskInputLogic;
import model.util.ConnectDB;
import model.util.TaskDAO;

/**
 * Servlet implementation class ToDoPage
 */
@WebServlet("/todo")
public class ToDoPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TaskInputLogic taskInputLogic = new TaskInputLogic();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToDoPage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO 直接このページに飛んで来たときに警告のページを表示
		//TODO 同じセッション中は何回もデータベースからとってこないようにしたい
		//TODO セッション切れの時ログインページに戻る
		//通信
		HttpSession session = request.getSession();
		Enumeration<String> parameterNames = request.getParameterNames();
		
		ConnectDB connectDB = new ConnectDB();
		TaskDAO taskDAO = new TaskDAO(connectDB.getConnection());
		
		//ユーザー
		User user = (User)session.getAttribute("user");
		List<Task> tasks = taskDAO.getTaskList(user.getUserName());
//		List<Task> tasks = user.getTasks();
		
		//ページ設定
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; UTF-8");
		//パラメーターの取得
		while(parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement().toString();
			System.out.println(paramName);
			
			switch(paramName) {
				case "APPEND" -> {
					String contents = request.getParameter("contents");
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
				// TODO 修正ボタン実装
				case "MODIFICATION" -> {
					String contents = request.getParameter("MODIFICATION");
					int index = Integer.parseInt(request.getParameter("index"));
					taskDAO.modificationTask(contents, tasks.get(index).getId());
				}
			}
		}
		
		//タスクをhtmlに出力する
		String taskListHtml = "";
		
		//TODO 日付が近い順	並べ替える
		for(int i = 0; i < tasks.size(); i++) {
			String contents = tasks.get(i).getContents();
			String term = tasks.get(i).getTerm();
			
			if(term == null) {
				term = "";
			}
			// TODO 完了されているタスクに関しては削除以外のボタンを表示しないようにする
//			String modifyHtml = "<input type=\"text\" value=\"" + contents + "\" name =\"MODIFICATION\">";
			
			if(tasks.get(i).isComplete()) {
				contents = "<s>" + contents + "</s>";
			}
			
			taskListHtml = taskListHtml 
				+ "<form action=\"\" method=\"post\">"
				+ "<li class=index"+i+">"
//				+ modifyHtml
				+ "<p class=\"contents\" style=\"display:inline;\">" + contents + " </div>"
				+ "<small>" + term + " </small>"
				+ "<input type=\"submit\" value=\"完了\" name=\"COMPLETE\">"
				+ "<input type=\"submit\" value=\"削除\" name=\"DELETE\">"
				+ "<input type=\"submit\" value=\"修正\" name =\"MODIFICATION\">"
				+ "<input type=\"hidden\" value=\"" + i + "\" name=\"index\">"
				+ "</li>"
				+ "</form>";

		}
		//ページ切り替え
		request.setAttribute("task",taskListHtml);
		String toDoPage = "./MyToDo.jsp";
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
