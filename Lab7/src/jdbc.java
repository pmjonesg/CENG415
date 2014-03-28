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
	            	firstName.fName(con);
	            }
	            else if(inputint == 2)
	            {
	            	lastName.lName(con);
	            }
	            else if(inputint == 3)
	            {
	            	courseNo.courseN(con);
	            }

	            else if(inputint == 4)
	            {
	                keyword.key(con);
	    	    }

	            else if(inputint == 5)
	            {
	               cost.courseCost(con);
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
