import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class newEnroll {

	public static void addCourses(Connection con) {
        try {
            Scanner scan = new Scanner(System.in);
            Scanner scan1 = new Scanner(System.in);
            
            System.out.println("Please enter an existing Student Id: ");
            int enrStudent = scan.nextInt();
            
            /* Check if student exists */
            String sql = "SELECT STUDENT_ID FROM STUDENT WHERE STUDENT_ID = ?";

            PreparedStatement prepStmt1 = con.prepareStatement(sql);
       		prepStmt1.setInt(1, enrStudent);
       		
       		ResultSet res = prepStmt1.executeQuery();
       		if(res.next() == false)
       		{
       			System.out.println("Student Id doesn't exist.\n");
       			return;
       		} else ;
            
            System.out.println("Please enter Course Nos to add (separated by commas): ");
            String enrCourses = scan.next();

       		String courseString[] = enrCourses.split(",");
       		for(int i = 0; i < courseString.length; i++)
       		{
       			if("".equals(courseString[i]))
               		i++;
       			
       			/* Parse course string to int */
       			int courseNum = Integer.parseInt(courseString[i].trim());
       			String sql3 = "SELECT COURSE_NO FROM SECTION WHERE COURSE_NO = ?";

                PreparedStatement prepStmt3 = con.prepareStatement(sql3);
                prepStmt3.setInt(1, courseNum);
               	
                /* Test if course no exists */
               	ResultSet res1 = prepStmt3.executeQuery();
               	if(res1.next() == false)
               	{
               		System.out.println("Course Number doesn't exist.\n");
               		scan1.close();
               		return;
               	} else ; 			
                
                /* Test if Section id exists */
                String sql4 = "SELECT SECTION_ID FROM SECTION WHERE COURSE_NO = ?";

                PreparedStatement prepStmt4 = con.prepareStatement(sql4);
           		prepStmt4.setInt(1, courseNum);
           		
           		ResultSet res2 = prepStmt4.executeQuery();
           		System.out.println("Section Id");
           		while(res2.next())
           		{
           			System.out.println(res2.getString("SECTION_ID"));
           		}
           		
           		System.out.println("Please enter an existing Section Id: ");
                int enrSection= scan1.nextInt();
           		
           		String sql1 = "SELECT SECTION_ID FROM SECTION WHERE SECTION_ID = ?";

                PreparedStatement prepStmt = con.prepareStatement(sql1);
           		prepStmt.setInt(1, enrSection);
           		ResultSet rs = prepStmt.executeQuery();
           		
           		if(rs.next() == false)
           		{
           			System.out.println("Section Id doesn't exist.\n");
           			scan1.close();
           			return;
           		} else ;
               	
               	String sql2 = "INSERT INTO ENROLLMENT("
               			+ "STUDENT_ID, SECTION_ID, ENROLL_DATE, FINAL_GRADE,"
                        + "CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE)"
                        + "VALUES (?, ?, TO_DATE(?, 'DD-MM-YY'), NULL, USER, SYSDATE, USER, SYSDATE)";
               	
               	System.out.println("Please enter Enroll date: ");
                String enrDate = scan1.next();
                
                PreparedStatement prepStmt2 = con.prepareStatement(sql2);
               	prepStmt2.setInt(1, enrStudent);
               	prepStmt2.setInt(2, enrSection);
               	prepStmt2.setString(3, enrDate);

           		prepStmt2.executeQuery();
       		}
       		
       		
            String sql2 = "SELECT STUDENT_ID, SECTION_ID, ENROLL_DATE, FINAL_GRADE,"
                        + "CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE FROM ENROLLMENT WHERE STUDENT_ID = ?";
            PreparedStatement prepStmt2 = con.prepareStatement(sql2);
            prepStmt2.setInt(1, enrStudent);
            ResultSet rs = prepStmt2.executeQuery();

            System.out.println("Student ID\tSection ID\t\tEnroll Date\t\tFinal Grade\t\tCreated By\t\tCreated Date\t\t\tModified By\t\tModified Date");
            while (rs.next()){
                System.out.println("\t" + rs.getString("STUDENT_ID") + "\t\t" + rs.getString("SECTION_ID") + "\t\t" + rs.getString("ENROLL_DATE") + "\t\t" + rs.getString("FINAL_GRADE") + "\t\t" + rs.getString("CREATED_BY") + "\t\t" + rs.getString("CREATED_DATE") + "\t\t" + rs.getString("MODIFIED_BY") + "\t\t" + rs.getString("MODIFIED_DATE"));
            }
            rs.close();
            System.out.println("\n");
        	
        } catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}