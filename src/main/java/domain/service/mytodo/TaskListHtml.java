package domain.service.mytodo;

import java.util.List;

import domain.model.Task;

public class TaskListHtml {	
	public String createHtml(List<Task> tasks) {
		StringBuilder html = new StringBuilder();
		
		html.append("<ul id=\"task_list\">");
		
		for(int i = 0; i < tasks.size(); i++) {
			String conetents = tasks.get(i).getContents();
			String term = tasks.get(i).getTerm();
			boolean isComplete = tasks.get(i).isComplete();
			
			html.append("<li class=\"tasklist-contents\">");
			html.append("<form id=\"tasks" + i + "\" action=\"\" method=\"post\">");
			html.append(createContents(conetents, isComplete,i));
			html.append(createTerm(term));
			if(!isComplete) {
				html.append(createInput("submit","submit", "完了", "COMPLETE"));
			}
			html.append(createInput("delete","submit", "削除", "DELETE"));
			html.append(createInput("index","hidden", String.valueOf(i) , "index"));
			html.append("</form>");
			html.append("</li>");
		}
		html.append("</ul>");
		
		return html.toString();
	}
	
	private String createInput(String id,String type,String value,String name) {
		StringBuilder html = new StringBuilder();
		
		html.append("<input ");
		html.append("id=\""+ id + "\"");
		html.append("type=\"" + type + "\"");
		html.append("value=\"" + value + "\"");
		html.append("name=\"" + name + "\"");
		html.append(">");
		
		return html.toString();
	}
	
	private String createContents(String contents, boolean isComplete,int index) {
		StringBuilder html = new StringBuilder();
		
		html.append("<div id=\"tasklist-contents" + index + "\" style=\"display:inline;\">");
		if(!isComplete) {
			html.append(contents);
		} else {
			html.append("<s>" + contents + "</s>");
		}
		html.append("</div>");
		return html.toString();
	}
	
	private String createTerm(String term) {
		StringBuilder html = new StringBuilder();
		html.append("<div id=\"tasklist-term\" style=\"display:inline;\">");
		if(term != null) {
			html.append("<small>" + term + "</small>");
		}
		html.append("</div>");
		
		return html.toString();
	}
}
