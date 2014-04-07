import java.lang.*;
import java.util.*;
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
            con.setAutoCommit(false);
			// 4. PROCESS
	        while(true)
	        {
	            System.out.println("1. Search by first name or last name\n2. Find sections by course_no\n3. Search a course by keyword(s)\n4. Find total cost of courses\n5. Add a Student\n6. Add an Instructor\n");
	            System.out.print("Enter an option (1-6) or 0 to quit: ");

	            Scanner in = new Scanner(System.in);
	            inputint =  in.nextInt();
	            if(inputint == 1)
	            {
	            	firstName.fName(con);
	            }
	            else if(inputint == 2)
	            {
	            	courseNo.courseN(con);
	            }
	            else if(inputint == 3)
	            {
	                keyword.key(con);
	    	    }
	            else if(inputint == 4)
	            {
	               cost.courseCost(con);
	            }
                else if(inputint == 5)
                {
                   addStudent.newStudent(con);
                }
                else if(inputint == 6)
                {
                	addInstructor.newInstructor(con);
                }
                else if(inputint == 7)
                {
                	addSection.newSection(con);
                }
                else if(inputint == 8)
                {
                	newEnroll.addCourses(con);
                }
	            else if(inputint == 0)
	            {
	            	System.out.println("The program has ended, closing connections...\n\n");
	            	break;
	            }
	            else
	            {
	                System.out.println("Enter a number between 1 and 5");
	                inputint = in.nextInt();
                }
	        }

			//5. CLOSE THE CONNECTION
			stmt.close();
			con.close();

		}
		catch(SQLException e){
			if(e instanceof SQLIntegrityConstraintViolationException) {
        		System.out.println("Zipcode already exists.");
			//System.out.println("SQL Exception: " + e.getMessage());
			//e.printStackTrace();
			}
		}
	}
}