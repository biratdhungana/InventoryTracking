package main;
import java.sql.*;

//MySQl Database created on MySQL Workbench GUI. 

public class Database {
	
	public Connection connect() {
		Connection conn = null;
		//TODO: THIS URL PATH NEEDS TO BE CHANGED ACCORDING TO DATABASE LOCATION IN RASPBERRY PI	
        String url = "jdbc:mysql:M:/SYSC3010/SYSC3010Project/SQL/Database.db";		//path to db file
        
	    try {
	    	conn = DriverManager.getConnection(url);  		//from imports. creates a connection to the DHU	            
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());				//else if it is null/not a valid path catches it and prints a string of this throwable
	    	}
	    return conn;
	    }
     
        
    public void insertUsername(String username, String password){
        String enterName = "INSERT INTO users (username, password) VALUES(?,?)";

        try (Connection conn = this.connect();
        PreparedStatement statementInsert = conn.prepareStatement(enterName)) {					//Validates connection to put desired string in database
        statementInsert.setString(2, username);											//assigns 2nd entry; he username	
        statementInsert.setString(3, password);											//assigns 3rd entry; the password
        statementInsert.executeUpdate();												
        System.out.println("Inserted into Database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());										//catches any exceptions that is not accounted for (invalid inputs)
        }
    }//finish insert into users

	public void insert(String id, Double x, Double y, Double z) {

		String sqlDelete = "DELETE FROM tags WHERE idTags=" + id;
		String sqlInsert = "INSERT INTO tags(idTags,xCoordinate,yCoordinate,zCoordinate) VALUES(?,?,?,?)";   //creates a insert method to accept data in
	 
			try (Connection conn = this.connect();
	            PreparedStatement statementDelete = conn.prepareStatement(sqlDelete)) {					//Validates connection to put desired string in database
	            statementDelete.executeUpdate();													//updates table here
	            System.out.println("Deleted from database");
			} catch (SQLException e) {
	            System.out.println(e.getMessage());										//catches any exceptions that is not accounted for (invalid inputs)
	        }
		
	        try (Connection conn = this.connect();
	            PreparedStatement statementInsert = conn.prepareStatement(sqlInsert)) {					//Validates connection to put desired string in database
	            statementInsert.setString(1, id);											//assigns first entry for tag id	
	            statementInsert.setDouble(2, x);													//assigns second entry for x coordinate
	            statementInsert.setDouble(3, y);													//assigns third entry for y coordinate
	            statementInsert.setDouble(4, z);													//assigns fourth entry for z coordinate
	            statementInsert.executeUpdate();													//updates table here
	            System.out.println("Inserted into Database");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());										//catches any exceptions that is not accounted for (invalid inputs)
	        }
	    }
	
	public double[] retrieveLastEntry(String tagId) {
		String s ="";
		double[] lastEntry = null;
		//String sql="SELECT * FROM (SELECT * FROM tags ORDER BY idTags DESC limit 1) ORDER BY idTags ASC";
		String sql = "SELECT * FROM tags WHERE idTags=" + tagId;
		try (Connection conn = this.connect();
				Statement st = conn.createStatement();
				ResultSet r = st.executeQuery(sql)){
			
			 s = (r.getString("idTags")+ ", " +
					r.getDouble("xCoordinate") + ", " +
					r.getDouble("yCoordinate") + ", " + 
					r.getDouble("zCoordinate"));
				
			System.out.println("retrieveLastEntry: " + s);
			
			lastEntry = new double[] {r.getDouble("xCoordinate"), r.getDouble("yCoordinate"), r.getDouble("zCoordinate")};
			
			
		} catch (SQLException e) {
			 //System.out.println(e.getMessage());
			 System.out.println("error");
		}
		
		return lastEntry;
		
	}
	
}

/*
import java.sql.*;
public class JavaMysqlSelectExample
{
  public static void main(String[] args)
  {
    try
    {
      // create our mysql database connection
      String myDriver = "org.gjt.mm.mysql.Driver";
      String myUrl = "jdbc:mysql://localhost/test";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "");
      
      // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT * FROM users";
      // create the java statement
      Statement st = conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      
      // iterate through the java resultset
      while (rs.next())
      {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Date dateCreated = rs.getDate("date_created");
        boolean isAdmin = rs.getBoolean("is_admin");
        int numPoints = rs.getInt("num_points");
        
        // print the results
        System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
      }
      st.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
  }
}
*/
