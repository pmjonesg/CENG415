mport java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class jdbc{
	
	public static void main(String[] args){
		try{
			//1. LOAD AND REGISTER THE DRIVER
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException x){
			System.out.println("Unable to load Driver class.");
			return;
		}

		try{
			String connString = "jdbc:oracle:thin:@//localhost:1521/msit.humber.ca";
			String userName="abcd0000";
			String password="oracle";

			// 2. CONNECT TO THE DATABASE
			Connection con = DriverManager.getConnection(connString,userName,password);
			
			// 3. CREATE A STATEMENT
			Statement stmt = con.createStatement();
			
			// 4. PROCESS
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

			//5. CLOSE THE CONNECTION
			rs.close();
			stmt.close();
			con.close();

		}
		catch(SQLException e){
			System.out.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
