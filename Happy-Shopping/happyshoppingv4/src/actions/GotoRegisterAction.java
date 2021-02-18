package actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GotoRegisterAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "gotoregister.success";
	}
}
