import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class keyword {
	public static void key(Connection con) {
		try {
			System.out.print("Please course keyword(s): ");
            Scanner scan = new Scanner(System.in);
            int count = 0;
			while(scan.hasNext())
			{	
		    if(count == 0) {
		    	System.out.println("\tCourse Number\t\tDescription");
		    	count++;
		    }
		    else {
		    String str = scan.next();
			String sql = "SELECT course_no, description FROM Course WHERE description LIKE ?";
			PreparedStatement prepStmt = con.prepareStatement(sql);
			prepStmt.setString(1, "%" + str + "%");
			ResultSet rs = prepStmt.executeQuery();
			System.out.println("\tCourse Number\t\tDescription");
			while (rs.next()){
				System.out.println("\t" + rs.getString("course_no") + "\t\t" + rs.getString("description"));
			}
			rs.close();
			
			System.out.print("\nType return to go back to the menu, continue to keep searching: ");
			Scanner scan1 = new Scanner(System.in);
			String s1 = scan1.next();
			if(s1.equals("return"))
            {
			    break;
            }
            else
				continue;
		    }
		    System.out.println("\n");
		} }catch(SQLException e){
			System.out.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
