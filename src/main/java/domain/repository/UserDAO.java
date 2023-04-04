package domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private Connection cn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public UserDAO(Connection cn) {
		this.cn = cn;
	}
	
	public boolean hasUserData(String name) {
		String sql = "SELECT name FROM users WHERE name = ?";
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rs.getString("name");
				return true;
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	public boolean appendUserData(String name) {
		String sql = "INSERT users(name) values(?)";
		try {
			pstmt = cn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			return true;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	public boolean selectUserData() {
		String sql = "SELECT name FROM users";
		
		try {
			pstmt = cn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rs.getString("name");
				return true;
			}
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
