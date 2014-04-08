import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;


public class dropStudent {
    public static void deleteStudent(Connection con) {
        try {
            Scanner scan = new Scanner(System.in);
          
            System.out.println("Please enter an existing Student Id: ");
            int dropStudent = scan.nextInt();
            
            /* Test if student id exists */
            String sql = "SELECT STUDENT_ID FROM STUDENT WHERE STUDENT_ID = ?";

            PreparedStatement prepStmt = con.prepareStatement(sql);
       		prepStmt.setInt(1, dropStudent);
       		
       		ResultSet res = prepStmt.executeQuery();
       		if(res.next() == false)
       		{
       			System.out.println("Student Id doesn't exist.\n");
       			return;
       		} else ;
            
            System.out.println("Please enter Course Number to drop: ");
            int dropCourse = scan.nextInt();			
            
            /* Check if course number exists */
   			String sql1 = "SELECT COURSE_NO FROM SECTION WHERE COURSE_NO = ?";

            PreparedStatement prepStmt1 = con.prepareStatement(sql1);
            prepStmt1.setInt(1, dropCourse);
           	
            /* Test if course no exists */
           	ResultSet res1 = prepStmt1.executeQuery();
           	if(res1.next() == false)
           	{
           		System.out.println("Course No doesn't exist.\n");
           		scan.close();
           		return;
           	} else ;
           	
           	String sql4 = "SELECT SECTION_ID FROM SECTION WHERE COURSE_NO = ?";

            PreparedStatement prepStmt4 = con.prepareStatement(sql4);
       		prepStmt4.setInt(1, dropCourse);
       		
       		ResultSet res2 = prepStmt4.executeQuery();
       		System.out.println("Section Id\n");
       		while(res2.next())
       		{
       			System.out.println(res2.getString("SECTION_ID"));
       		}
       		
       		System.out.println("Please enter an existing Section Id: ");
            int dropSection= scan.nextInt();
       		
       		String sql2 = "SELECT SECTION_ID FROM SECTION WHERE SECTION_ID = ?";

            PreparedStatement prepStmt2 = con.prepareStatement(sql2);
       		prepStmt2.setInt(1, dropSection);
       		ResultSet res3 = prepStmt2.executeQuery();
       		
       		if(res3.next() == false)
       		{
       			System.out.println("Section Id doesn't exist.\n");
       			scan.close();
       			return;
       		} else ;
            
            String sql3 = "SELECT TO_CHAR(START_DATE_TIME) FROM SECTION WHERE SECTION_ID = ? AND SYSDATE < START_DATE_TIME";
            PreparedStatement prepStmt3 = con.prepareStatement(sql3);
            prepStmt3.setInt(1, dropSection);
            ResultSet res4 = prepStmt3.executeQuery();
            
            if(res4.next() == false)
       		{
       			System.out.println("Start date of course has already begun.\n");
       			scan.close();
       			return;
       		} else ;
            
            String sql6 = "DELETE FROM GRADE WHERE STUDENT_ID = ? AND SECTION_ID = ?";
            PreparedStatement prepStmt6 = con.prepareStatement(sql6);
            prepStmt6.setInt(1, dropStudent);
            prepStmt6.setInt(2, dropSection);
            prepStmt6.executeQuery();
            
            String sql7 = "DELETE FROM ENROLLMENT WHERE STUDENT_ID = ? AND SECTION_ID = ? AND SYSDATE < (SELECT START_DATE_TIME FROM SECTION WHERE SECTION_ID = ?)";
            PreparedStatement prepStmt7 = con.prepareStatement(sql7);
            prepStmt7.setInt(1, dropStudent);
            prepStmt7.setInt(2, dropSection);
            prepStmt7.setInt(3, dropSection);
            prepStmt7.executeQuery();
            
            System.out.println("Student Succesfully removed.");
            System.out.println("\n");
            
        } catch(SQLException e){
        	if(e.getCause() != null && e.getCause() instanceof SQLIntegrityConstraintViolationException)
        		System.out.println("Zipcode already exists.");
        	else {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        	}
        }
    }
}