/* Name: Alyson Franco
 * Course: CNT 4714 – Summer 2024 – Project Three
 * Assignment title: A Three-Tier Distributed Web-Based Application
 * Date: August 1, 2024
 */

import java.sql.*;

public class ResultSetToHTMLFormatter {

	public static synchronized String convert(ResultSet results) throws SQLException {

		// htmlRows is a StringBuffer object that will hold the HTML version of the
		// ResultSet in the outbound direction.
		StringBuffer htmlRows = new StringBuffer();
		// Need the metaData belonging to the inbound ResultSet object.
		ResultSetMetaData metaData = results.getMetaData();
		// Need to know the number of columns in the ResultSet object - number of rows
		// not important.
		int columnCount = metaData.getColumnCount();

		// set the table header row
		htmlRows.append("<table>");
		htmlRows.append("<tr>");

		// append the HTML table header row to outbound htmlRows object
		for (int i = 1; i <= columnCount; i++) {
			htmlRows.append("<th>" + metaData.getColumnName(i) + "</th>");
		}
		// close of header row </th>
		htmlRows.append("</tr>");

		// set the remainder of the table - row-by-row
		// set counter if you want css zebra row string effect
		int rowCount = 0;
		while (results.next()) {

			for (int i = 1; i <= columnCount; i++) {
				htmlRows.append("<td>" + results.getString(i) + "</td>");
			}
			htmlRows.append("</tr>");
			rowCount++;
		}
		htmlRows.append("</table>");

		// convert htmlRows object to a String and return to caller
		return htmlRows.toString();

	} // end getHtmlRows () method

} // end ResultSetToHTMLFormatterClass