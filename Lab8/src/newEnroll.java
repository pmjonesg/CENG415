import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class newEnroll {
	public static void addCourses(Connection con) {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Please enter an existing Student Id: ");
            int enrStudent= scan.nextInt();
            System.out.println("Please enter Course Nos to add (separated by commas): ");
            scan.useDelimiter(",");
            
            String sql = "SELECT STUDENT_ID FROM STUDENT WHERE STUDENT_ID = ?";

            PreparedStatement prepStmt1 = con.prepareStatement(sql);
       		prepStmt1.setInt(1, enrStudent);
       		
       			ResultSet res = prepStmt1.executeQuery();
       			if(res.next() == false)
       			{
       				System.out.println("Student Id doesn't exist.\n");
       				return;
       			} else ;
       		
       		while(scan.hasNext())
       		{
       			Scanner scan1 = new Scanner(System.in);
       			String courseString = scan.next();
       			if("\n".equals(courseString))
               		break;
       			
       			/* Convert parsed string to int */
       			int courseNum = Integer.parseInt(courseString.trim());
       			String sql3 = "SELECT COURSE_NO FROM COURSE WHERE COURSE_NO = ?";

                PreparedStatement prepStmt3 = con.prepareStatement(sql3);
                prepStmt3.setInt(1, courseNum);
               	
                /* Test if course no exists */
               	ResultSet res1 = prepStmt3.executeQuery();
               	if(res1.next() == false)
               	{
               		System.out.println("Course No doesn't exist.\n");
               		scan1.close();
               		return;
               	} else ; 			
       		
               	/* Add a new section for course */
               	String sql1 = "INSERT INTO SECTION("
            			+ "SECTION_ID, COURSE_NO, SECTION_NO, START_DATE_TIME,"
            			+ "LOCATION, INSTRUCTOR_ID, CAPACITY,"
            			+ "CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE)"
            			+ "VALUES ((SELECT MAX(SECTION_ID) FROM SECTION) + 1, ?, (SELECT MAX(SECTION_NO) FROM SECTION) + 1, TO_DATE(?, 'DD-MM-YY'), ?, ?, ?, USER, SYSDATE, USER, SYSDATE)";
               	
               	System.out.println("Please enter Start date: ");
                String enrStart = scan1.next();
                System.out.println("Please enter Location: ");
                String enrLocation = scan1.next();
                System.out.println("Please enter an existing Instructor Id: ");
                int enrInstructor= scan1.nextInt();
                System.out.println("Please enter Capacity: ");
                int enrCapacity = scan1.nextInt();
                
                /* Test if instructor id exists */
                String sql4 = "SELECT INSTRUCTOR_ID FROM INSTRUCTOR WHERE INSTRUCTOR_ID = ?";

                PreparedStatement prepStmt4 = con.prepareStatement(sql4);
           		prepStmt4.setInt(1, enrInstructor);
           		
           		ResultSet res2 = prepStmt4.executeQuery();
           		if(res2.next() == false)
           		{
           			System.out.println("Instructor Id doesn't exist.\n");
           			scan1.close();
           			return;
           		} else ;
               	
           		/* Add entry to section */
               	PreparedStatement prepStmt = con.prepareStatement(sql1);
               	prepStmt.setInt(1, courseNum);
               	prepStmt.setString(2, enrStart);
               	prepStmt.setString(3, enrLocation);
               	prepStmt.setInt(4, enrInstructor);
               	prepStmt.setInt(5, enrCapacity);
               	prepStmt.executeQuery();
               	
               	String sql2 = "INSERT INTO ENROLLMENT("
               			+ "STUDENT_ID, SECTION_ID, ENROLL_DATE, FINAL_GRADE,"
                        + "CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE"
                        + "VALUES (?, ?, TO_DATE(?, 'DD-MM-YY'), NULL, USER, SYSDATE, USER, SYSDATE";
               	
               	System.out.println("Please enter Enroll date: ");
                String enrDate = scan1.next();
                
                String sql5 = "SELECT MAX(SECTION_ID) FROM SECTION";
                PreparedStatement prepStmt5 = con.prepareStatement(sql5);
                ResultSet rs5 = prepStmt5.executeQuery();
                int enrSectionID = rs5.getInt("SECTION_ID");
                
               	PreparedStatement prepStmt2 = con.prepareStatement(sql2);
               	prepStmt.setInt(1, enrStudent);
               	prepStmt.setInt(2, enrSectionID);
               	prepStmt.setString(3, enrDate);

           		prepStmt2.executeQuery();        	
       		}
       		
       		
            String sql2 = "SELECT STUDENT_ID, SECTION_ID, ENROLL_DATE, FINAL_GRADE,"
                        + "CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE FROM SECTION WHERE STUDENT_ID = ?";
            PreparedStatement prepStmt2 = con.prepareStatement(sql2);
            prepStmt2.setInt(1, enrStudent);
            ResultSet rs = prepStmt2.executeQuery();


            System.out.println("Student ID\tSection ID\tEnroll Date\tFinal Grade\tCreated By\tCreated Date\tModified By\tModified Date");
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