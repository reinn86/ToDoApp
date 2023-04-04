package domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Task;

public class TaskDAO {
	private Connection cn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public TaskDAO(Connection cn) {
		this.cn = cn;
	}
	
	public List<Task> getTaskList(String name) {
		String sql = "SELECT * FROM tasks WHERE user_id = (SELECT id from users where name = ?)";
		List<Task> tasks = new ArrayList<>();
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setContents(rs.getString("contents"));
				task.setTerm(rs.getString("term"));
				task.setComplete(rs.getBoolean("is_completed"));
				tasks.add(task);
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return tasks;
	}
	
	public boolean appendTaskData(String name,String contents,String term) {
		String sql = "INSERT tasks(user_id,contents,term,is_completed) "
				+ "values((SELECT id from users where name = ?),?,?,false)";
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, contents);
			pstmt.setString(3, term);
			pstmt.executeUpdate();
			return true;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	public boolean appendTaskData(String name,String contents,String term,boolean isCompleted) {
		String sql = "INSERT tasks(user_id,contents,term,is_completed) values((SELECT id from users where name = ?),?,?,?)";
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, contents);
			pstmt.setString(3, term);
			pstmt.setBoolean(4, isCompleted);
			pstmt.executeUpdate();
			return true;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	//TODO 無駄に名前とってきてるから削除する
	public boolean completeTask(String name,int id) {
		String sql = "UPDATE tasks SET is_completed = true "
				+ "WHERE user_id = (SELECT id from users where name = ?) "
				+ "and id = ?";
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			return true;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	public boolean modificationTask(String contents,int id) {
		String sql = "UPDATE tasks SET contents = ? "
				+ "WHERE id = ?";
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, contents);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			return true;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	public boolean deleteTask(String name,int id) {
		String sql = "DELETE FROM tasks "
				+ "WHERE user_id = (SELECT id from users where name = ?) "
				+ "and id = ?";
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			return true;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	private void close() {
		try {
			if(cn != null) {
				cn.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
