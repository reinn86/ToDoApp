package domain.service.mytodo;

import java.util.List;

import domain.model.Task;

public class TaskListHtml {
	String id= "process";
	
	public String createHtml(List<Task> tasks) {
		StringBuilder html = new StringBuilder();
		
		html.append("<ul>");
		
		for(int i = 0; i < tasks.size(); i++) {
			String conetents = tasks.get(i).getContents();
			String term = tasks.get(i).getTerm();
			boolean isComplete = tasks.get(i).isComplete();
			
			html.append("<li>");
			html.append("<form action=\"\" method=\"post\">");
			html.append("<p id=\"" + id + "\" style=\"display:inline;\">");
			html.append(createContents(conetents, isComplete));
			html.append(createTerm(term));
			html.append("</p> ");
			if(!isComplete) {
				html.append(createInput("submit", "完了", "COMPLETE"));
			}
			html.append(createInput("submit", "削除", "DELETE"));
			html.append(createInput("hidden", String.valueOf(i) , "index"));
			html.append("</form>");
			html.append("</li>");
		}
		html.append("</ul>");
		
		return html.toString();
	}
	
	private String createInput(String type,String value,String name) {
		StringBuilder html = new StringBuilder();
		
		html.append("<input ");
		html.append("type=\"" + type + "\"");
		html.append("value=\"" + value + "\"");
		html.append("name=\"" + name + "\"");
		html.append(">");
		
		return html.toString();
	}
	
	private String createContents(String contents, boolean isComplete) {
		String html;
		
		if(!isComplete) {
			html = contents;
		} else {
			html = "<s>" + contents + "</s>";
		}
		
		return html;
	}
	
	private String createTerm(String term) {
		String html;
		
		if(term != null) {
			html = " <small>" + term + "</small>";
		} else {
			html = " ";
		}
		
		return html;
	}
}
