import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Acc.Login;
public class MainController {
	static Connection conn = null;
	public static Connection connection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost:3306/MSMS?" +
		                                   "user=root&password=noumanSERVER123*");
		    if(conn != null) {
		    	System.out.println("Connection to database created");
		    }
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		return conn;
	}
	public static void main(String[] args) {
		conn = connection();
		@SuppressWarnings("unused")
		Login login = new Login(conn);
	}

}

