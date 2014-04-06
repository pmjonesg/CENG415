import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;


public class addStudent {
    public static void newStudent(Connection con) throws SQLException {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter salutation (Mr. Ms.): ");
            String  studentSalutation= scan.next();
            System.out.println("Please enter First Name: ");
            String studentFname = scan.next();
            System.out.println("Please enter Last Name: ");
            String studentLname = scan.next();
            System.out.println("Please enter Street Address: ");
            String studentAddr = scan.next();
            System.out.println("Please enter Zip: ");
            String studentZip = scan.next();
            System.out.println("Please enter Phone: ");
            String studentPhone = scan.next();
            System.out.println("Please enter Employer: ");
            String studentEmployer = scan.next();

            String sql = "INSERT INTO ZIPCODE(ZIP, CITY, STATE, CREATED_BY, CREATED_DATE,"
                         + "MODIFIED_BY, MODIFIED_DATE)"
                         + "VALUES(?, NULL, NULL, USER, SYSDATE, USER, SYSDATE)";

            String sql1 = "INSERT INTO STUDENT("
                         + "STUDENT_ID, SALUTATION, FIRST_NAME, LAST_NAME,"
                         + "STREET_ADDRESS, ZIP, PHONE, EMPLOYER, REGISTRATION_DATE,"
                         + "CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE)"
                         + "VALUES (400, ?, ?, ?, ?, ?, ?, ?, SYSDATE, USER, SYSDATE, USER, SYSDATE)";
           
            PreparedStatement prepStmt1 = con.prepareStatement(sql);
            prepStmt1.setString(1, studentZip);
            try {
            ResultSet res = prepStmt1.executeQuery();
            } catch(Exception e) {
            	System.out.println("Zipcode already exists.");
            	return;
            }
            
            PreparedStatement prepStmt = con.prepareStatement(sql1);
            prepStmt.setString(1, studentSalutation);
            prepStmt.setString(2, studentFname);
            prepStmt.setString(3, studentLname);
            prepStmt.setString(4, studentAddr);
            prepStmt.setString(5, studentZip);
            prepStmt.setString(6, studentPhone);
            prepStmt.setString(7, studentEmployer);

            ResultSet rs1 = prepStmt.executeQuery();
            rs1.close();
            
            String sql2 = "SELECT STUDENT_ID, SALUTATION, FIRST_NAME, LAST_NAME, STREET_ADDRESS,"
                        + "ZIP, PHONE, EMPLOYER, REGISTRATION_DATE, CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE FROM STUDENT WHERE ZIP LIKE ?";
            
            PreparedStatement prepStmt2 = con.prepareStatement(sql2);
            prepStmt2.setString(1, studentZip);
            ResultSet rs = prepStmt2.executeQuery();

            System.out.println("Student ID\tSalutation\tFirst Name\tLast Name\tStreet Address\tZip\tPhone Number\tEmployer\tRegistration Date\tCreated By\tCreated Date\tModified By\tModified Date");
            while (rs.next()){
                System.out.println("\t" + rs.getString("student_id") + "\t\t" + rs.getString("Salutation") + "\t\t" + rs.getString("FIRST_NAME") + "\t\t" + rs.getString("LAST_NAME") + "\t\t" + rs.getString("STREET_ADDRESS") + "\t\t" + rs.getString("ZIP") + "\t\t" + rs.getString("PHONE") + "\t\t" + rs.getString("EMPLOYER") + "\t\t" + rs.getString("REGISTRATION_DATE") + "\t\t" + rs.getString("CREATED_BY") + "\t\t" + rs.getString("CREATED_DATE") + "\t\t" + rs.getString("MODIFIED_BY") + "\t\t" + rs.getString("MODIFIED_DATE"));
            }
            rs.close();
            System.out.println("\n");
        } catch(SQLException e){
        	if(e instanceof SQLIntegrityConstraintViolationException) {
        		System.out.println("Zipcode already exists.");
        		return;
        	} else {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        	}
        }
    }
}
