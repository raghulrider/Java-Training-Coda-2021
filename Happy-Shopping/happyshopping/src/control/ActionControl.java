package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.DBProps;


public class ActionControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
		private RequestProcessor rp;
		
		@Override
		public void init(ServletConfig config) throws ServletException {
			rp = new RequestProcessor();
			
			String configDir = config.getInitParameter("config");
			String dbConfigDir = config.getInitParameter("dbconfig");
			
			ServletContext ctx = config.getServletContext();
			String configPath = ctx.getRealPath(configDir);
			String dbConfigPath = ctx.getRealPath(dbConfigDir);
			
			Properties configFile = new Properties();
			Properties dbConfigFile = new Properties();
			try {
				configFile.load(new FileInputStream(configPath));
				dbConfigFile.load(new FileInputStream(dbConfigPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
			ctx.setAttribute("configFile", configFile);
			DBProps.setDriver(dbConfigFile.getProperty("driver"));
			DBProps.setUrl(dbConfigFile.getProperty("url"));
			DBProps.setUsername(dbConfigFile.getProperty("username"));
			DBProps.setPassword(dbConfigFile.getProperty("password"));
		}

		synchronized protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
		    session.setMaxInactiveInterval(60);
			
			rp=rp.getClone();
			rp.process(request, response);
		}

		
		synchronized protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
		}

	}
