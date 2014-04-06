import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;


public class addInstructor {
	public static void newInstructor(Connection con) {
        try {
            Scanner scan = new Scanner(System.in);
          
            System.out.println("Please enter salutation (Mr. Ms.): ");
            String  instSalutation= scan.next();
            System.out.println("Please enter First Name: ");
            String instFname = scan.next();
            System.out.println("Please enter Last Name: ");
            String instLname = scan.next();
            System.out.println("Please enter Street Address: ");
            String instAddr = scan.next();    
            System.out.println("Please enter Zip: ");
            String instZip = scan.next();
            System.out.println("Please enter Phone: ");
            String instPhone = scan.next();
			
            String sql = "INSERT INTO ZIPCODE(ZIP, CITY, STATE, CREATED_BY, CREATED_DATE,"
                         + "MODIFIED_BY, MODIFIED_DATE)"
                         + "VALUES(?, NULL, NULL, USER, SYSDATE, USER, SYSDATE)";
    
            PreparedStatement prepStmt1 = con.prepareStatement(sql);
            prepStmt1.setString(1, instZip);
            try {
            	ResultSet res = prepStmt1.executeQuery();
            } catch (Exception e) {
            	System.out.println("Zipcode already exists.\n");
            	return;
            }
            
            String sql1 = "INSERT INTO INSTRUCTOR("
                    + "INSTRUCTOR_ID, SALUTATION, FIRST_NAME, LAST_NAME,"
                    + "STREET_ADDRESS, ZIP, PHONE,"
                    + "CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE)"
                    + "VALUES ((SELECT MAX(INSTRUCTOR_ID) FROM INSTRUCTOR) + 1, ?, ?, ?, ?, ?, ?, USER, SYSDATE, USER, SYSDATE)";
            
            PreparedStatement prepStmt = con.prepareStatement(sql1);
            prepStmt.setString(1, instSalutation);
            prepStmt.setString(2, instFname);
            prepStmt.setString(3, instLname);
            prepStmt.setString(4, instAddr);
            prepStmt.setString(5, instZip);
            prepStmt.setString(6, instPhone);

            ResultSet rs1 = prepStmt.executeQuery();

            
            String sql2 = "SELECT INSTRUCTOR_ID, SALUTATION, FIRST_NAME, LAST_NAME, STREET_ADDRESS,"
                        + "ZIP, PHONE, CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE FROM INSTRUCTOR WHERE ZIP LIKE ?";
            PreparedStatement prepStmt2 = con.prepareStatement(sql2);
            prepStmt2.setString(1, instZip);
            ResultSet rs = prepStmt2.executeQuery();


            System.out.println("Instructor ID\tSalutation\tFirst Name\tLast Name\tStreet Address\tZip\tPhone Number\tCreated By\tCreated Date\tModified By\tModified Date");
            while (rs.next()){
                System.out.println("\t" + rs.getString("INSTRUCTOR_ID") + "\t\t" + rs.getString("Salutation") + "\t\t" + rs.getString("FIRST_NAME") + "\t\t" + rs.getString("LAST_NAME") + "\t\t" + rs.getString("STREET_ADDRESS") + "\t\t" + rs.getString("ZIP") + "\t\t" + rs.getString("PHONE") + "\t\t" + rs.getString("CREATED_BY") + "\t\t" + rs.getString("CREATED_DATE") + "\t\t" + rs.getString("MODIFIED_BY") + "\t\t" + rs.getString("MODIFIED_DATE"));
            }
            rs.close();
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
