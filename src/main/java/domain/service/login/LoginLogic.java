package domain.service.login;

public class LoginLogic {
	boolean a = true;
	
	public boolean excute(String name) {
		if(name != null && name.length() >= 1) {
			return true; 
		}
		return false;
	}
}
