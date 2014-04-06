import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;


public class addSection {
	public static void newSection(Connection con) {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Please enter an existing Instructor Id: ");
            int secInstructor= scan.nextInt();
            System.out.println("Please enter Course No to add: ");
            int secCourse = scan.nextInt();
            System.out.println("Please enter Start date: ");
            String secStart = scan.next();
            System.out.println("Please enter Location: ");
            String secLocation = scan.next();
            System.out.println("Please enter Capacity: ");
            int secCapacity = scan.nextInt();
            
            String sql = "SELECT INSTRUCTOR_ID FROM INSTRUCTOR WHERE INSTRUCTOR_ID = ?";

            PreparedStatement prepStmt1 = con.prepareStatement(sql);
       		prepStmt1.setInt(1, secInstructor);
       		
       			ResultSet res = prepStmt1.executeQuery();
       			if(res.next() == false)
       			{
       				System.out.println("Instructor Id doesn't exist.\n");
       				return;
       			} else ;
       		        
       		String sql3 = "SELECT COURSE_NO FROM COURSE WHERE COURSE_NO = ?";

            PreparedStatement prepStmt3 = con.prepareStatement(sql3);
            prepStmt3.setInt(1, secCourse);
           		
           	ResultSet res1 = prepStmt3.executeQuery();
           	if(res1.next() == false)
           	{
           		System.out.println("Course No doesn't exist.\n");
           		return;
           	} else ;
           			
            String sql1 = "INSERT INTO SECTION("
            			+ "SECTION_ID, COURSE_NO, SECTION_NO, START_DATE_TIME,"
            			+ "LOCATION, INSTRUCTOR_ID, CAPACITY,"
            			+ "CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE)"
            			+ "VALUES ((SELECT MAX(SECTION_ID) FROM SECTION) + 1, ?, (SELECT MAX(SECTION_NO) FROM SECTION) + 1, TO_DATE(?, 'DD-MM-YY'), ?, ?, ?, USER, SYSDATE, USER, SYSDATE)";
            
            PreparedStatement prepStmt = con.prepareStatement(sql1);
            prepStmt.setInt(1, secCourse);
            prepStmt.setString(2, secStart);
            prepStmt.setString(3, secLocation);
            prepStmt.setInt(4, secInstructor);
            prepStmt.setInt(5, secCapacity);

            prepStmt.executeQuery();

            
            String sql2 = "SELECT SECTION_ID, COURSE_NO, SECTION_NO, START_DATE_TIME, LOCATION,"
                        + "INSTRUCTOR_ID, CAPACITY, CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE FROM SECTION WHERE INSTRUCTOR_ID = ?";
            PreparedStatement prepStmt2 = con.prepareStatement(sql2);
            prepStmt2.setInt(1, secInstructor);
            ResultSet rs = prepStmt2.executeQuery();


            System.out.println("Section ID\tCourse No\tSection No\tStart Date\tLocation\tInstructor ID\tCapacity\tCreated By\tCreated Date\tModified By\tModified Date");
            while (rs.next()){
                System.out.println("\t" + rs.getString("SECTION_ID") + "\t\t" + rs.getString("COURSE_NO") + "\t\t" + rs.getString("SECTION_NO") + "\t\t" + rs.getString("START_DATE_TIME") + "\t\t" + rs.getString("LOCATION") + "\t\t" + rs.getString("INSTRUCTOR_ID") + "\t\t" + rs.getString("CAPACITY") + "\t\t" + rs.getString("CREATED_BY") + "\t\t" + rs.getString("CREATED_DATE") + "\t\t" + rs.getString("MODIFIED_BY") + "\t\t" + rs.getString("MODIFIED_DATE"));
            }
            rs.close();
            System.out.println("\n");
        
        } catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}