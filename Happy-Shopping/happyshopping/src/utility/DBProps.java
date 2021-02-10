package utility;

public class DBProps {
	private static String driver, url, username, password;

	public static String getDriver() {
		return driver;
	}

	public static void setDriver(String driver) {
		DBProps.driver = driver;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		DBProps.url = url;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		DBProps.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		DBProps.password = password;
	}
}
