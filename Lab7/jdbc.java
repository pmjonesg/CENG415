import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class jdbc{
	
	public static void main(String[] args){
	
		int inputint;
		
		try{
			//1. LOAD AND REGISTER THE DRIVER
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException x){
			System.out.println("Unable to load Driver class.");
			return;
		}

		try{
			String connString = "jdbc:oracle:thin:@//munro.humber.ca:1521/msit.humber.ca";
			String userName="jnsp0116";
			String password="oracle";

			// 2. CONNECT TO THE DATABASE
			Connection con = DriverManager.getConnection(connString,userName,password);
			
			// 3. CREATE A STATEMENT
			Statement stmt = con.createStatement();
			
			// 4. PROCESS
			String input = "loop";
	        while(!input.equals("exit"))
	        {
	            System.out.println("1. Search by first name\n2. Search by last name\n3. Find sections by course_no\n4. Search a course by keyword(s)\n5. Find total cost of courses\n");
	            System.out.print("Enter an option (1-5) or 0 to quit: ");
	            
	            Scanner in = new Scanner(System.in);
	            inputint =  in.nextInt();
	            if(inputint == 1)
	            {
	            	System.out.println("Please enter the student's first name: ");
	    			Scanner scan = new Scanner(System.in);
	    			String stuName = scan.next();
	    			String sql = "SELECT first_name, last_name FROM Student WHERE first_name LIKE ?";
	    			PreparedStatement prepStmt = con.prepareStatement(sql);
	    			prepStmt.setString(1, "%" + stuName + "%");
	    			
	    			ResultSet rs = prepStmt.executeQuery();

	    			System.out.println("\tFirst Name\t\tLast Name");
	    			while (rs.next()){
	    				System.out.println("\t" + rs.getString("first_name") + "\t\t" + rs.getString("last_name"));
	    			}
	    			System.out.println("\n");
	    			rs.close();
	            }
	            else if(inputint == 2)
	            {
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
	            }
	            else if(inputint == 3)
	            {
	            	System.out.println("Please a course number: ");
	    			Scanner scan = new Scanner(System.in);
	    			String courseNo = scan.next();
	    			String sql = "SELECT section_id, course_no FROM Section WHERE course_no LIKE ?";
	    			PreparedStatement prepStmt = con.prepareStatement(sql);
	    			prepStmt.setString(1, "%" + courseNo + "%");
	    			
	    			ResultSet rs = prepStmt.executeQuery();

	    			System.out.println("\tSection\t\tCourse Number");
	    			while (rs.next()){
	    				System.out.println("\t" + rs.getString("section_id") + "\t\t" + rs.getString("course_no"));
	    			}
	    			System.out.println("\n");
	    			rs.close();
	            }
	           
	            else if(inputint == 4)
	            {
	                System.out.println("Please course keyword(s): ");
	    			Scanner scan = new Scanner(System.in);
	    			String courseDesc = scan.next();
	    			String sql = "SELECT course_no, description FROM Course WHERE description LIKE ?";
	    			PreparedStatement prepStmt = con.prepareStatement(sql);
	    			prepStmt.setString(1, "%" + courseDesc + "%");
	    			
	    			ResultSet rs = prepStmt.executeQuery();

	    			System.out.println("\tCourse Number\t\tDescription");
	    			while (rs.next()){
	    				System.out.println("\t" + rs.getString("course_no") + "\t\t" + rs.getString("description"));
	    			}
	    			System.out.println("\n");
	    			rs.close();
	            }
	           
	            else if(inputint == 5)
	            {
	                System.out.println("Please enter Student ID: ");
	    			Scanner scan = new Scanner(System.in);
	    			Integer courseStudent = scan.nextInt();
	    			System.out.println("Please enter Year: ");
	    			Scanner scanYear = new Scanner(System.in);
	    			String courseYear = scanYear.next();
	    			String sql = "SELECT e.student_id, EXTRACT(YEAR FROM e.ENROLL_DATE) AS endate, c.course_no, c.cost"
	    						+ " FROM ENROLLMENT e"
	    						+ " JOIN Section s  ON s.section_id = e.SECTION_ID"
	    						+ " JOIN COURSE c   ON c.COURSE_NO = s.COURSE_NO"
	    						+ " WHERE e.STUDENT_ID LIKE ? AND EXTRACT(YEAR FROM e.ENROLL_DATE) IN ?";
	    			PreparedStatement prepStmt = con.prepareStatement(sql);
	    			prepStmt.setInt(1, courseStudent);
	    			prepStmt.setString(2, courseYear);
	    			
	    			ResultSet rs = prepStmt.executeQuery();

	    			System.out.println("Student ID\tRegistration Date\tCourse Number\tCost");
	    			while (rs.next()){
	    				System.out.println("\t" + rs.getString("student_id") + "\t\t" + rs.getString("endate") + "\t\t" + rs.getString("course_no") + "\t\t" + rs.getString("cost"));
	    			}
	    			System.out.println("\n");
	    			rs.close();
	            }
	            
	            else if(inputint == 0)
	            {
	            	System.out.println("The program has ended, closing connections...\n\n");
	            	break;
	            }
	            else
	            {
	                System.out.println("Enter a number between 1 and 5: ");
	                inputint = in.nextInt();
	            }
	            }
	  
			//5. CLOSE THE CONNECTION
			stmt.close();
			con.close();

		}
		catch(SQLException e){
			System.out.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
