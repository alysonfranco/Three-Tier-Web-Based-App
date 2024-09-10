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

public class AddShipmentRecordServlet extends HttpServlet {

	private Connection connection;
	private PreparedStatement statement;
	private int mysqlUpdateValue;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String message = "";
		String btnPress = request.getParameter("btnPress");

		// check for a button press
		if ("Clear Data and Results".equals(btnPress)) {
			message = "";
		} else if ("Enter Shipment Record Into Database".equals(btnPress)) {
			try {
				connection = getDBConnection();
				if (connection == null) {
					message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> Unable to connect to database.</font></td></tr></table>";
				} else {

					// getting params
					String snum = request.getParameter("snum");
					String pnum = request.getParameter("pnum");
					String jnum = request.getParameter("jnum");
					String quantity = request.getParameter("quantity");

					// checking for int value
					try {
						int quantityInt = Integer.parseInt(quantity);
					} catch (NumberFormatException e) {
						message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> Invalid input for weight. Please enter a numeric value.</font></td></tr></table>";
						request.setAttribute("message", message);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
						dispatcher.forward(request, response);
						return;
					}

					// query to insert values entered
					String updatingCommand = "INSERT INTO shipments VALUES (?, ?, ?, ?)";
					statement = connection.prepareStatement(updatingCommand);

					statement.setString(1, snum);
					statement.setString(2, pnum);
					statement.setString(3, jnum);
					statement.setString(4, quantity);

					mysqlUpdateValue = statement.executeUpdate();

					if (mysqlUpdateValue != 0) {
						// business logic for shipments table
						String businessLogicCommand = "update suppliers set status = status + 5 where snum in (select snum from shipments where quantity >= 100)";
						int businessLogicUpdateValue = statement.executeUpdate(businessLogicCommand);

						// check for business logic trigger
						boolean businessLogicTriggered = businessLogicUpdateValue > 0;

						// execution results message
						message = "<table><tr style='background-color: #0000ff;'><td><font color='#ffffff'>New Shipment Record ("
								+ snum + ", " + pnum + ", " + jnum + ", " + quantity
								+ ") - successfully entered into database. ";
						if (businessLogicTriggered) {
							message += "Business logic triggered!</font></td></tr></table>";
						} else {
							message += "Business logic not triggered.</font></td></tr></table>";
						}
					} else {
						// Record error
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
