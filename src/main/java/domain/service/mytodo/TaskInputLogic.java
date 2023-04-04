package domain.service.mytodo;

public class TaskInputLogic {
	public boolean excute(String content) {
		if(content != null && content.length() >= 1) {
			return true;
		}
		return false;
	}
}
