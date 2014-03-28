import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class cost {
	public static void courseCost(Connection con) {
		try {
			System.out.println("Please enter Student ID: ");
			Scanner scan = new Scanner(System.in);
			Integer courseStudent = scan.nextInt();
			System.out.println("Please enter Year: ");
			Scanner scanYear = new Scanner(System.in);
			String courseYear = scanYear.next();
			String sql = "SELECT student_id, EXTRACT(YEAR FROM ENROLL_DATE) AS endate, SUM(cost) AS COST"
						+ " FROM ENROLLMENT e"
						+ " JOIN Section s  ON s.section_id = e.SECTION_ID"
						+ " JOIN COURSE c   ON c.COURSE_NO = s.COURSE_NO"
						+ " GROUP BY student_id, enroll_date"
						+ " HAVING e.STUDENT_ID LIKE ? AND EXTRACT(YEAR FROM e.ENROLL_DATE) IN ?";
			PreparedStatement prepStmt = con.prepareStatement(sql);
			prepStmt.setInt(1, courseStudent);
			prepStmt.setString(2, courseYear);

			ResultSet rs = prepStmt.executeQuery();

			System.out.println("Student ID\tRegistration Date\tCourse Number\tCost");
			while (rs.next()){
				System.out.println("\t" + rs.getString("student_id") + "\t\t" + rs.getString("endate") + "\t\t" + rs.getString("COST"));
			}
			System.out.println("\n");
			rs.close();
		} catch(SQLException e){
			System.out.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
