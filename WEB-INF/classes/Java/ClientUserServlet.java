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

public class ClientUserServlet extends HttpServlet {
	private Connection connection; // normal user command connection
	private Statement statement; // statement object for sending commands to DB
	private int mysqlUpdateValue; // value returned by updating command

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Get the client user command from the .html/.jsp front-end
		String sqlStatement = request.getParameter("command"); // inbound user command
		String btnPress = request.getParameter("btnPress");
		// Set up the message string for the response back to the client front-end
		String message = ""; // outbound response
		if ("Reset Form".equals(btnPress)) {
			// Handle form reset
			sqlStatement = "";
		} else if ("Clear Result".equals(btnPress)) {
			// Handle clear result
			message = "";
		} else if ("Execute Command".equals(btnPress)) {
			try {
				// Get a connection to the database
				connection = getDBConnection();
				if (connection == null) {
					message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> Unable to connect to database.</font></td></tr></table>";
				} else {
					// Create a Statement object
					statement = connection.createStatement();
					// Handle inbound user command
					String inboundCommand = sqlStatement.trim();

					if (inboundCommand.toLowerCase().startsWith("select")) {
						// Execute JDBC query and get ResultSet back
						ResultSet resultSet = statement.executeQuery(inboundCommand);
						// Convert ResultSet into an HTML table
						message = ResultSetToHTMLFormatter.convert(resultSet);
					} else {
						// Command is an update
						mysqlUpdateValue = statement.executeUpdate(inboundCommand);
						message = "<table><tr style='background-color: #00ff00;'><td><font color='#000000'>The statement executed successfully. "
								+ mysqlUpdateValue + " row(s) affected.</font></td></tr></table>";
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
				message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error executing the SQL statement:</b><br>"
						+ e.getMessage() + "</font></td></tr></table>";
			} finally {
				// Close statement but not connection
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
		session.setAttribute("sqlStatement", sqlStatement);

		// Create a RequestDispatcher object for handling return to user front-end page
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/clientHome.jsp");
		dispatcher.forward(request, response);
	} // end doPost() method

	// get db connection method
	private Connection getDBConnection() {
		Properties properties = new Properties();
		MysqlDataSource dataSource = null;
		try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/lib/client.properties")) {

			if (input == null) {
				System.err.println("Properties file not found!");
				return null;
			}

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

}// end ClientUserApp
