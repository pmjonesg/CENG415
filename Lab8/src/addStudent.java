import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;


public class addStudent {
    public static void newStudent(Connection con) {
        try {
            Scanner scan = new Scanner(System.in);
          
            System.out.println("Please enter salutation (Mr. Ms.): ");
            String  studentSalutation= scan.nextLine();
            System.out.println("Please enter First Name: ");
            String studentFname = scan.nextLine();
            System.out.println("Please enter Last Name: ");
            String studentLname = scan.nextLine();
            System.out.println("Please enter Street Address: ");
            String studentAddr = scan.nextLine();    
            System.out.println("Please enter Zip: ");
            String studentZip = scan.nextLine();
            
            /* Check if zip exists */
            String sql = "SELECT ZIP FROM ZIPCODE WHERE ZIP LIKE ?";
            
            PreparedStatement prepStmt1 = con.prepareStatement(sql);
            prepStmt1.setString(1, studentZip);
            ResultSet res = prepStmt1.executeQuery();
            if(res.next() == false){
            	System.out.println("Zipcode doesn't exist");
            	return;
            } else ;
            
            System.out.println("Please enter Phone: ");
            String studentPhone = scan.nextLine();
            System.out.println("Please enter Employer: ");
            String studentEmployer = scan.nextLine();
            
            String sql1 = "INSERT INTO STUDENT("
                    + "STUDENT_ID, SALUTATION, FIRST_NAME, LAST_NAME,"
                    + "STREET_ADDRESS, ZIP, PHONE, EMPLOYER, REGISTRATION_DATE,"
                    + "CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE)"
                    + "VALUES ((SELECT MAX(STUDENT_ID) FROM STUDENT) + 1, ?, ?, ?, ?, ?, ?, ?, SYSDATE, USER, SYSDATE, USER, SYSDATE)";
            
            PreparedStatement prepStmt = con.prepareStatement(sql1);
            prepStmt.setString(1, studentSalutation);
            prepStmt.setString(2, studentFname);
            prepStmt.setString(3, studentLname);
            prepStmt.setString(4, studentAddr);
            prepStmt.setString(5, studentZip);
            prepStmt.setString(6, studentPhone);
            prepStmt.setString(7, studentEmployer);
            
            prepStmt.executeQuery();

            String sql2 = "SELECT STUDENT_ID, SALUTATION, FIRST_NAME, LAST_NAME, STREET_ADDRESS,"
                        + "ZIP, PHONE, EMPLOYER, REGISTRATION_DATE, CREATED_BY, CREATED_DATE,"
                        + "MODIFIED_BY, MODIFIED_DATE FROM STUDENT WHERE ZIP LIKE ?";
            
            PreparedStatement prepStmt2 = con.prepareStatement(sql2);
            prepStmt2.setString(1, studentZip);
            ResultSet rs = prepStmt2.executeQuery();


            System.out.println("Student ID\tSalutation\t\tFirst Name\tLast Name\tStreet Address\t\tZip\tPhone Number\t\tEmployer\t\tRegistration Date\t\tCreated By\t\tCreated Date\t\tModified By\t\tModified Date");
            while (rs.next()){
                System.out.println("\t" + rs.getString("student_id") + "\t\t" + rs.getString("Salutation") + "\t\t" + rs.getString("FIRST_NAME") + "\t\t" + rs.getString("LAST_NAME") + "\t\t" + rs.getString("STREET_ADDRESS") + "\t\t" + rs.getString("ZIP") + "\t\t" + rs.getString("PHONE") + "\t\t" + rs.getString("EMPLOYER") + "\t\t" + rs.getString("REGISTRATION_DATE") + "\t\t" + rs.getString("CREATED_BY") + "\t\t" + rs.getString("CREATED_DATE") + "\t\t" + rs.getString("MODIFIED_BY") + "\t\t" + rs.getString("MODIFIED_DATE"));
            }
            rs.close();  
            System.out.println("\n");
            
        } catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        	}
        }
    }