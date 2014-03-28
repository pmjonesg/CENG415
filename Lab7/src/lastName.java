import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class lastName {

	public static void lName(Connection con) {
		
		try {
			System.out.println("Please enter the student's last name: ");
			Scanner scan = new Scanner(System.in);
			String stuName = scan.next();
			String sql = "SELECT first_name, last_name FROM Student WHERE last_name LIKE ?";
			PreparedStatement prepStmt = con.prepareStatement(sql);
			prepStmt.setString(1, "%" + stuName + "%");

			ResultSet rs = prepStmt.executeQuery();

			System.out.println("\tFirst Name\t\tLast Name");
			while (rs.next()){
				System.out.println("\t" + rs.getString("first_name") + "\t\t" + rs.getString("last_name"));
			}
			System.out.println("\n");
			rs.close();
		}catch(SQLException e){
			System.out.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	} 
	
}
