package control;

import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import actions.Action;

public class RequestProcessor implements Cloneable {

	synchronized public RequestProcessor getClone() {
		try {
			return (RequestProcessor) super.clone();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	synchronized public void process(HttpServletRequest request, HttpServletResponse response) {
		String formID = request.getParameter("formid");
		HttpSession session = request.getSession();
		System.out.println(formID);
		ServletContext ctx = request.getServletContext();
		Properties configFile = (Properties) ctx.getAttribute("configFile");
		String actionClass = configFile.getProperty(formID);
		int gst = Integer.parseInt(configFile.getProperty("gst"));
		int discount = Integer.parseInt(configFile.getProperty("discount"));
		session.setAttribute("gst", gst);
		session.setAttribute("discount", discount);
		System.out.println(actionClass);
		try {
			Action action = (Action) Class.forName(actionClass).getDeclaredConstructor().newInstance();
			String result = action.execute(request, response);
			String pageToLoad = configFile.getProperty(result);
			System.out.println("Page to load : "+pageToLoad);
			if(!formID.equals("downloadbill")) {
				RequestDispatcher rd = request.getRequestDispatcher(pageToLoad);
				rd.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
