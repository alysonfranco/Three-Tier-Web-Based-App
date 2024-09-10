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

public class AccountantServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get the user's RPC choice
		// inbound command parameter is a String, so need to parse to an integer value.
		String parameter = request.getParameter("cmd");
		String btnPress = request.getParameter("btnPress");

		// local variables
		String message = "";
		String command = "";

		// clear results regardless of radio selection
		if ("Clear Result".equals(btnPress)) {
			message = "";
		} else if ("Execute Command".equals(btnPress)) { // execute commands when btn pressed
			if (parameter == null) { // error message
				message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> No option selected. Please select one of the options above.</font></td></tr></table>";
			} else {
				int cmdValue = Integer.parseInt(parameter); // parsing cmd parameter to integer

				try (Connection connection = getDBConnection()) { // get a connection to the database
					if (connection == null) {
						message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> Unable to connect to database.</font></td></tr></table>";
					} else {

						// get the user's selection ready to process
						// String updatingCommand = "insert into suppliers values (?, ? , ? ,?)";
						switch (cmdValue) {
						case 1:
							command = "{call Get_The_Maximum_Status_Of_All_Suppliers()}";
							break;
						case 2:
							command = "{call Get_The_Sum_Of_All_Parts_Weights()}";
							break;
						case 3:
							command = "{call Get_The_Total_Number_Of_Shipments()}";
							break;
						case 4:
							command = "{call Get_The_Name_Of_The_Job_With_The_Most_Workers()}";
							break;
						case 5:
							command = "{call List_The_Name_And_Status_Of_All_Suppliers()}";
							break;
						default:
							message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b>Nothing select. Please select one of the options above.</font></td></tr></table>";
							break;
						}

						try (CallableStatement statement = connection.prepareCall(command)) {
							boolean mysqlReturnValue = statement.execute();
							if (mysqlReturnValue) { // if mysqlReturnValue is true, then a ResultSet object has been
													// returned
								ResultSet resultSet = statement.getResultSet(); // get results from the RPC
								// query
								message = ResultSetToHTMLFormatter.convert(resultSet); // get
								// ResultSet formatted for return
							} else {
								message = "<table><tr style='background-color: #ff0000;'><td><font color='#ffffff'><b>Error:</b> Error executing RPC!</font></td></tr></table>";
							}

						} catch (SQLException e) {
							message = "<tr bgcolor=#ff0000><td><font color=#ffffff><b>Error executing the SQL statement :</b><br>"
									+ e.getMessage() + "</tr></td></font>";
						}
					}

				} catch (SQLException e) {
					message = "<tr bgcolor=#ff0000><td><font color=#ffffff><b>Error establishing the connection :</b><br>"
							+ e.getMessage() + "</tr></td></font>";
				}
			}
		}

		// set session attribute values for return

		// Insert code block for accessing the HttpSession object
		HttpSession session = request.getSession();
		session.setAttribute("message", message);
		session.setAttribute("sqlStatement", command);

		// use dispatcher object to return results.
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/accountantHome.jsp");
		dispatcher.forward(request, response);
	} // end doPost() method

	// get db connection method
	private Connection getDBConnection() {
		Properties properties = new Properties();
		MysqlDataSource dataSource = null;
		try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/lib/theaccountant.properties")) {

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
} // end class AccountantServlet