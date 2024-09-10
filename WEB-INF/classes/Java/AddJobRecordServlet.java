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

public class AddJobRecordServlet extends HttpServlet {

	private Connection connection;
	private PreparedStatement statement;
	private int mysqlUpdateValue;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String message = "";
		String btnPress = request.getParameter("btnPress");

		// check for a button press
		if ("Clear Data and Results".equals(btnPress)) {
			message = "";
		} else if ("Enter Job Record Into Database".equals(btnPress)) {
			try {
				connection = getDBConnection();
				if (connection == null) {
					message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> Unable to connect to database.</font></td></tr></table>";
				} else {

					// getting params
					String jnum = request.getParameter("jnum");
					String jname = request.getParameter("jname");
					String numworkers = request.getParameter("numworkers");
					String city = request.getParameter("city");

					// checking for int value
					try {
						int numWorkersInt = Integer.parseInt(numworkers);
					} catch (NumberFormatException e) {
						message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> Invalid input for numworkers. Please enter a numeric value.</font></td></tr></table>";
						request.setAttribute("message", message);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
						dispatcher.forward(request, response);
						return;
					}

					// query to insert values entered
					String updatingCommand = "INSERT INTO jobs VALUES (?, ?, ?, ?)";
					statement = connection.prepareStatement(updatingCommand);

					statement.setString(1, jnum);
					statement.setString(2, jname);
					statement.setString(3, numworkers);
					statement.setString(4, city);

					mysqlUpdateValue = statement.executeUpdate();

					// check for valid update
					if (mysqlUpdateValue != 0) {
						message = "<table><tr style='background-color: #0000ff;'><td><font color='#ffffff'>New Part Record ("
								+ jnum + ", " + jname + ", " + numworkers + ", " + city
								+ ") - successfully entered into database.</font></td></tr></table>";
					} else { // error if no update
						message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error entering record into the database. Database not updated!</font></td></tr></table>";
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error executing the SQL statement:</b><br>"
						+ e.getMessage() + "</font></td></tr></table>";
			} finally {
				// Close statement but not connection after execute button is pressed
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		// Insert code block for accessing the HttpSession object
		HttpSession session = request.getSession();
		session.setAttribute("message", message);

		// Create a RequestDispatcher object for handling return to user front-end page
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
		dispatcher.forward(request, response);
	}

	// get db connection method
	private Connection getDBConnection() {
		Properties properties = new Properties();
		MysqlDataSource dataSource = null;
		try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/lib/dataentry.properties")) {
			if (input == null) {
				System.err.println("Properties file not found!");
				return null;
			}
			properties.load(input);
			dataSource = new MysqlDataSource();
			dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
			dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
			dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
			return dataSource.getConnection();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
