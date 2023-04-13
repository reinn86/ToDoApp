package domain.service.login;

public class LoginLogic {	
	public boolean excute(String inputName) {
		String filterName = inputName;
		
		if(filterName != null && filterName.length() >= 1) {
			return true; 
		}
		return false;
	}
}
