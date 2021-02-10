package control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCheckFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest=(HttpServletRequest)request;
		HttpServletResponse hresponse=(HttpServletResponse)response;
		HttpSession session=hrequest.getSession();
		if(session.isNew()) {
			System.out.println("New Session");
			hresponse.sendRedirect("/happyshopping/sessiontimedout.jsp");
			}else {
			System.out.println("Not a new Session");
			chain.doFilter(request, response);	
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
