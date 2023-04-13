package app.security;

public class HtmlFilter {
	public String inputHtmlFilter(String str) {
		String filterStr = str;
		if(filterStr != null) {
			filterStr = filterStr.replace("\"", "&quot;");
			filterStr = filterStr.replace("&", "&amp;");
			filterStr = filterStr.replace("<","&lt;");
			filterStr = filterStr.replace(">","&gt;");
			System.out.println(str);
			System.out.println(filterStr);
		}
		return filterStr;
	}
}
