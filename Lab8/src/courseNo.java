import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class courseNo {

	public static void courseN (Connection con) {
		try {
			System.out.println("Please a course number: ");
			Scanner scan = new Scanner(System.in);
			Integer courseNo = scan.nextInt();
			String sql = "SELECT section_id, course_no FROM Section WHERE course_no LIKE ?";
			PreparedStatement prepStmt = con.prepareStatement(sql);
			prepStmt.setInt(1, courseNo);

			ResultSet rs = prepStmt.executeQuery();

			System.out.println("\tSection\t\tCourse Number");
			while (rs.next()){
				System.out.println("\t" + rs.getString("section_id") + "\t\t" + rs.getString("course_no"));
			}
			System.out.println("\n");
			rs.close();
		} catch(SQLException e){
			System.out.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
