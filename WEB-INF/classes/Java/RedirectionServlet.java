/* Name: Alyson Franco
 * Course: CNT 4714 – Summer 2024 – Project Three
 * Assignment title: A Three-Tier Distributed Web-Based Application
 * Date: August 1, 2024
 */

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class RedirectionServlet extends HttpServlet {

	private Connection connection;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// processRequest(request, response);
	}

	// process "get" request from client
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// logout redirection for all pages
		// closes db connecton and redirects to login page
		String btnPress = request.getParameter("btnPress");

		if ("Logout".equals(btnPress)) {
			// Handle logout button press
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			closeConnection(); // Explicitly close the database connection
			response.sendRedirect("authentication.html");
			return;
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// checking for connection
		try (Connection connection = getDBConnection()) {
			if (connection == null) {
				response.sendRedirect("errorpage.html");
				return;
			}

			// query to login user
			String query = "SELECT login_username FROM usercredentials WHERE login_username = ? AND login_password = ?";

			// try to build prepared statement
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, username);
				statement.setString(2, password);

				// try to execute query
				try (ResultSet resultSet = statement.executeQuery()) {
					// if user is found, redirect to appropriate page
					if (resultSet.next()) {
						String userLevel = resultSet.getString("login_username");
						// switch case to redirect page based on user level
						switch (userLevel) {
						case "root":
							response.sendRedirect("rootHome.jsp");
							break;
						case "client":
							response.sendRedirect("clientHome.jsp");
							break;
						case "dataentryuser":
							response.sendRedirect("dataentryHome.jsp");
							break;
						case "theaccountant":
							response.sendRedirect("accountantHome.jsp");
							break;
						default:
							response.sendRedirect("errorpage.html");
							break;
						} // end of switch case
						return;
					} else {
						response.sendRedirect("errorpage.html");
						return;
					} // end of if statement
				} // end of try with resources - resultSet
			} // end of try with resources - statement
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("errorpage.html");
		} // end of try with resources - connection
	}

	// get db connection method
	private Connection getDBConnection() {
		Properties properties = new Properties();
		MysqlDataSource dataSource = null;
		try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/lib/systemapp.properties")) {

			// load properties file
			properties.load(input);

			dataSource = new MysqlDataSource();
			dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
			dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
			dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

			// establishes the connection to the database
			return dataSource.getConnection();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} // end of getDBConnection method

	// close connection method
	private void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
