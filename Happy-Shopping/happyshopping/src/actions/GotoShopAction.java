package actions;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GotoShopAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		boolean result=false;
		Enumeration<String> en = session.getAttributeNames();
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			if(!key.equals("employeeId")) {
				result=true;
				session.removeAttribute(key);
			}
		}
		if(result) {
			return "gotoshop.success";
		}else {
			return "gotoshop.failure";
		}
		
	}
}
