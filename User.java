
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


// Asif - This class will have everything a user may use.
//The design pattern of ToDo class can help in this case.
//There will be another table having User name and password. Sql queries will be handled by ToDoqueries. 
// This class will have the ability to hold all the ids of tasks the user itself has.
//So that each time we store a user means storing the id of the task that specific user has listed 
//on Todo List.
// List can be used to hold the info's. Every possible function ( Including setters and getters ) 
//should be written with appropriate parameters. 
// Depending on Sql queries and gui, the code might be changed. So User class in general having 
//everything a user can use is preferred. 

public class User {

  //===========================================================================
  // FIELDS
  //===========================================================================
  private static String username;
  private static String password;

  final int  pass_size = 4;

  //===========================================================================
  // CONSTRUCTOR
  //===========================================================================
  User(){
    this.username = null;
    this.password = null;
  }

  User(String a, String b){
    this.username = a;
    this.password = b;
		
		/*if ( a = null || b = null){
			System.out.println("invalid data");
			
		}*/
  }


  //===========================================================================
  // METHODS
  //===========================================================================
  // GETTERS
  //---------------------------------------------------------------------------
  public String getusername(){
    return this.username;
  }
  public String getpassword(){
    return this.password;
  }

  //---------------------------------------------------------------------------
  // SETTERS
  //---------------------------------------------------------------------------
  public void setusername(String s){
    this.username = s;
  }
  public void setpassword( String a){
    if (a.length()<= pass_size && a.length() > 0){
      this.password = a;
    }

  }
  //---------------------------------------------------------------------------
  // OTHER METHODS
  //---------------------------------------------------------------------------
// TODO: Aziz - look at this code for login logic
//  public boolean loginCheck(String a, String pass){
//
//    String sql;
//    ResultSet rs;
//    boolean login = false;
//
//    try {
//
//      ToDoQueries t = new ToDoQueries();
//      Class.forName("org.hsqldb.jdbcDriver");
//      Connection connection = DriverManager.getConnection(t.URL, t.USERNAME, t.PASSWORD);
//      java.sql.Statement s;
//
//      sql = "SELECT username, password FROM USERS WHERE username='" + a + "' AND password='" + pass + "';";
//
//      //rs = s.executeQuery(sql);
//      try {
//        s = connection.createStatement();
//        rs = s.executeQuery(sql);
//        login = rs.first();
//      } catch (SQLException e) {
//        e.printStackTrace();
//      }
//
//    } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return login;
//  }
	
/*

	public List<ToDo> allusertasks(String name){
		String sql;
	    List<ToDo> results = null;
	    ResultSet resultSet = null;
	    
	    try {
	    	
	    	ToDoQueries t = new ToDoQueries();
	        Class.forName("org.hsqldb.jdbcDriver");
	        Connection connection = DriverManager.getConnection(t.URL, t.USERNAME, t.PASSWORD);
	        java.sql.Statement s;
	        
	        sql = "SELECT * FROM TODO WHERE user = '"+ name +"';";

		    try {
		      // executeQuery returns ResultSet containing matching entries
		    	s= connection.createStatement();
		      resultSet = s.executeQuery(sql);
		      results = new ArrayList<ToDo>();

		      while (resultSet.next()) {
		        results.add(
		        		new ToDo(resultSet.getString("ID"), resultSet.getString("Category"))
		        );
		    	  
		      }
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        resultSet.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		        close();
		      }
		    }

		    return results;
	}
	
	public String toString() {
		
	    return String.format("%3s\t%-15s", this.username, this.password);
	  }
	
/*	public static void main(String[] args) {
		String a = "asif";
		String h = "hey";
		
		User u = new User();
		u.setpassword(h);
		u.setusername(a);
		String b = u.getusername();
		String p = u.getpassword();
		System.out.println(b);
		System.out.println(p);
		
		
		}
	*/

}