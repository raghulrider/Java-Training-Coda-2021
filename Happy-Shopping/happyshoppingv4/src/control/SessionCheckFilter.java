package control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebFilter("*.do")
public class SessionCheckFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest hrequest=(HttpServletRequest)request;
		HttpSession session=hrequest.getSession();
		
		if(session.isNew()) {
			System.out.println("New Session");
			RequestDispatcher rd = request.getRequestDispatcher("sessiontimedout.jsp");
			rd.forward(request, response);
			}else {
			System.out.println("Not a new Session");
			chain.doFilter(request, response);	
		}
	}
}
