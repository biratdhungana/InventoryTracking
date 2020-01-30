package main;
import java.sql.*;

//SQLite Database created on command prompt. code is as follows

/*M:\sql>sqlite3 DHU                                                              //Database created here
SQLite version 3.21.0 2017-10-24 18:55:49
Enter ".help" for usage hints.
sqlite> create table DHU_Database(ID INTEGER PRIMARY KEY AUTOINCREMENT, Temperature Float, Fire INTEGER, Smoke INTEGER, Time DATETIME DEFAULT(STRFTIME('%d-%m-%Y  %H:%M', 'NOW', 'localtime'))); //Table created here
sqlite> INSERT INTO DHU_Database(Temperature, Fire, Smoke) VALUES (24.0,0,0);	  //Inserting fake values to check if the database and table is active
sqlite> INSERT INTO DHU_Database(Temperature, Fire, Smoke) VALUES (23.0,1,1);
sqlite> INSERT INTO DHU_Database(Temperature, Fire, Smoke) VALUES (20.0,1,0);
sqlite> SELECT*FROM DHU_Database												  //Prints the specified table present in the database
   ...> ;
1|24.0|0|0|27-11-2017  12:58
2|23.0|1|1|27-11-2017  12:59
3|20.0|1|0|27-11-2017  12:59
sqlite>
*/

public class Database {
	

	public Connection connect() {
		Connection conn = null;
		String url = "jdbc:mysql:M:/SYSC3010/SYSC3010Project/SQL/Database.db";		//path to db file
	    try {
	    	conn = DriverManager.getConnection(url);  		//from imports. creates a connection to the DHU	            
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());				//else if it is null/not a valid path catches it and prints a string of this throwable
	    	}
	    return conn;
	    }
	 
	public void insert(String id, Double x, Double y, Double z) {
		//int fire, smoke;
		//fire = Fire ? 1:0;									//since DHU unit passes boolean values and the database uses
		//smoke = Smoke ? 1:0;								//integers, this simple loop assigns a 1 if true else assigns false. 
															//This is for Fire and Smoke values only

		String sql = "INSERT INTO tags(idTags,xCoordinate,yCoordinate,zCoordinate) VALUES(?,?,?,?)";   //creates a insert method to accept data in
	 
	        try (Connection conn = this.connect();
	            PreparedStatement statement = conn.prepareStatement(sql)) {					//Validates connection to put desired string in database
	            statement.setString(1, id);											//assigns first entry for Temperature	
	            statement.setDouble(2, x);													//assigns second entry for fire
	            statement.setDouble(3, y);													//assigns third entry for smoke
	            statement.setDouble(4, z);
	            statement.executeUpdate();													//updates table here
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());										//catches any exceptions that is not accounted for (invalid inputs)
	        }
	    }
	
	public String retrieveLastEntry() {
		String s ="";
		String sql="SELECT * FROM (SELECT * FROM tags ORDER BY idTags DESC limit 1) ORDER BY idTags ASC";
		try (Connection conn = this.connect();
				Statement st = conn.createStatement();
				ResultSet r = st.executeQuery(sql)){
			
			 s = (r.getString("idTags")+ "," +
					r.getDouble("xCoordinate") + "," +
					r.getDouble("yCoordinate") + "," + 
					r.getDouble("zCoordinate"));
			//System.out.println(s);
			
		} catch (SQLException e) {
			 //System.out.println(e.getMessage());
			 System.out.println("error");
		}
		
		return s;
	}

	
	public void getFire(boolean Fire){
		int fire;
		if (Fire = true) {
			fire = 0;
		} else {
			fire = -1;
		}
		
		String sql = "SELECT ID, Temperature, Fire, Smoke "								//sql query to select an entry
                       + "FROM DHU_Database WHERE Fire > ?";
            
		try (Connection conn = this.connect();
				PreparedStatement ptsd  = conn.prepareStatement(sql)){					//validates connection
			ptsd.setInt(1,fire);														//sets the value 
			ResultSet rs  = ptsd.executeQuery();										//Executes query
         
			while (rs.next()) {															//while loop through loop through database and 
				System.out.println(rs.getInt("ID") +  "\t" + 							//get all results where fire entry is true
            		 			rs.getFloat("Temperature") + "\t" +
                                rs.getInt("Fire") + "\t" +
                                rs.getInt("Smoke"));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());										//catches any exceptions
				}
		}
	
	public void getSmoke(boolean Smoke){
		int smoke;
		if(Smoke = true) {
			smoke = 0;
		} else {
			smoke = -1;
		}
		
		String sql = "SELECT ID, Temperature, Fire, Smoke "								//sql query to select an entry
                       + "FROM DHU_Database WHERE Fire > ?";
            
		try (Connection conn = this.connect();
				PreparedStatement ptsd  = conn.prepareStatement(sql)){					//validates connection
			ptsd.setInt(1,smoke);														//sets the value 
			ResultSet rs  = ptsd.executeQuery();										//Executes query
         
			while (rs.next()) {															//while loop through loop through database and 
				if (rs.next() == true) {
				System.out.println(rs.getInt("ID") +  "\t" + 							//get all results where fire entry is true
            		 			rs.getFloat("Temperature") + "\t" +
                                rs.getInt("Fire") + "\t" +
                                rs.getInt("Smoke"));
				}
		}
			} catch (SQLException e) {
				System.out.println(e.getMessage());										//catches any exceptions
				}
		}
	
		public String getAllEntries() {
		String s ="";
		String sql="select * from (select * from DHU_Database) order by ID ASC";
		try (Connection conn = this.connect();
				Statement st = conn.createStatement();
				ResultSet r = st.executeQuery(sql)){
		
			while(r.next()) {
			 s = (r.getInt("ID")+ "\t" +
					r.getFloat("Temperature") + "\t" +
					r.getInt("Fire") + "\t" + 
					r.getInt("Smoke") + "\t" +
					r.getString("Time"));
			System.out.println(s);
			}
			
		} catch (SQLException e) {
			 //System.out.println(e.getMessage());
			 System.out.println("error");
		}
		return s;
	}
	/*	public String reOrderDatabase() {
		DELETE FROM DHU_Database
		SELECT*FROM DHU_Database;
	}*/
	
    /*public static void main(String[] args) {
        Database x = new Database();												//used to test connection and functionality of the database
        x.retrieveLastEntry();
    }*/
	

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
