package actions;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShoppingAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		Enumeration<String> en = request.getParameterNames();
		System.out.println(en.nextElement());
		Map<String, String> items = new HashMap<>();
		
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			String value  = request.getParameter(key);
			if(!value.equals("0")) {
				items.put(key, value);
			}else {
				System.out.println("Culprit : "+key+value);
			}
		}
		session.setAttribute("items", items);
		return "shop.success";
	}
}
