package actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllReportAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		return "allreport.success";
	}
}
