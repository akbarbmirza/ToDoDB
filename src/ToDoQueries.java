import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on Fig. 24.31: PersonQueries.java
 * PreparedStatements used by the ToDo Application.
 */


public class ToDoQueries {

  //===========================================================================
  // FIELDS
  //===========================================================================

  private static final String URL = "jdbc:hsqldb:testdb";
  private static final String USERNAME = "sa";
  private static final String PASSWORD = "";

  private Connection connection; // manages connection
  
  //Sql Statements For TODO Table
  private PreparedStatement selectAllTasksFromTodoTable;
  private PreparedStatement selectTaskByIDFromTodoTable;
  private PreparedStatement insertNewTaskToTodoTable;
  
  
  //Sql Statements For CATEGORIES Table
  private PreparedStatement selectAllTasksFromCategoriesTable;
  private PreparedStatement selectTaskByIDFromCategoriesTable;
  private PreparedStatement insertNewTaskToCategoriesTable;
  
  // See all columns together
  private PreparedStatement selectAllTasks;

  //===========================================================================
  // CONSTRUCTOR
  //===========================================================================

  public ToDoQueries() {
    try {
      Class.forName("org.hsqldb.jdbcDriver");
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

      Statement s;
      String sql;
      ResultSet rs;
      
      System.out.println("Connection established");
      // DROP TODO TABLE
		sql = "DROP TABLE TODO;";
		try {
			s = connection.createStatement();
			rs = s.executeQuery(sql);
			} catch (SQLException e) {
			e.printStackTrace();
		}

//   // DROP CATEGORIES TABLE
//            sql = "DROP TABLE CATEGORIES;";
//            try {
//            s = connection.createStatement();
//            rs = s.executeQuery(sql);
//            } catch (SQLException e) {
//              e.printStackTrace();
//            }

 
      //************************* TODO Table ********************************
      
      // CREATE TODO TABLE
      sql = "CREATE TABLE TODO(ID INT, Task VARCHAR(255), Done BOOLEAN NOT NULL, Date DATE );";
      
      try {
    	  s = connection.createStatement();
    	  rs = s.executeQuery(sql);
      } 
      catch (SQLException e) {
    	  e.printStackTrace();
      }

      // create query that selects all entries from the TODO Table
      sql = "SELECT * FROM TODO";
      selectAllTasksFromTodoTable = connection.prepareStatement(sql);

      // create query that selects entries with a specific ID
      sql = "SELECT * FROM TODO WHERE ID = ?";
      selectTaskByIDFromTodoTable = connection.prepareStatement(sql);

      // create insert that adds a new task into the database
      sql = "INSERT INTO TODO (ID, Task, Done, Date) VALUES (?, ?, ?, ?)";
      insertNewTaskToTodoTable = connection.prepareStatement(sql);
      
      // ******************* CATEGORIES TABLE ************************************** 
      
      // CREATE SECOND TABLE
      sql = "CREATE TABLE CATEGORIES (ID INT, Category VARCHAR(255));";
      try {
    	  s = connection.createStatement();
    	  rs = s.executeQuery(sql);
      } 
      catch (SQLException e) {
    	  e.printStackTrace();
      }

	// create query that selects all entries from the CATEGORIES Table
	sql = "SELECT TODO.ID, TODO.Task, CATEGORIES.Category, TODO.Done, TODO.Date FROM TODO INNER JOIN CATEGORIES ON TODO.ID=CATEGORIES.ID";
	selectAllTasks = connection.prepareStatement(sql);
	
	sql = "SELECT * FROM CATEGORIES";
	selectAllTasksFromCategoriesTable = connection.prepareStatement(sql);
	
	// create query that selects entries with a specific ID
	sql = "SELECT * FROM CATEGORIES WHERE ID = ?";
	selectTaskByIDFromCategoriesTable = connection.prepareStatement(sql);
	
	// create insert that adds a new task into the database
	sql = "INSERT INTO CATEGORIES (ID, Category) VALUES (?, ?)";
	insertNewTaskToCategoriesTable = connection.prepareStatement(sql);
    
   
    
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
  }

  //===========================================================================
  // METHODS
  //===========================================================================
  // GETTERS
  //---------------------------------------------------------------------------

  // select all of the tasks in the database
  public List<ToDo> getAllTasksFromTodoTable() {
    List<ToDo> results = null;
    ResultSet resultSet = null;

    try {
      // executeQuery returns ResultSet containing matching entries
      resultSet = selectAllTasksFromTodoTable.executeQuery();
      results = new ArrayList<ToDo>();

      while (resultSet.next()) {
        results.add(
                new ToDo(resultSet.getString("Task"), null)
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

  // FOR CATEOGIRES TABLE
  
  public List<ToDo> getAllTasksFromCategoriesTable() {
	    List<ToDo> results = null;
	    ResultSet resultSet = null;

	    try {
	      // executeQuery returns ResultSet containing matching entries
	      resultSet = selectAllTasksFromCategoriesTable.executeQuery();
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
  
  // For inner joinof both of the tables
  
  public List<ToDo> getAllTasks() {
	    List<ToDo> results = null;
	    ResultSet resultSet = null;

	    try {
	      // executeQuery returns ResultSet containing matching entries
	      resultSet = selectAllTasks.executeQuery();
	      results = new ArrayList<ToDo>();

	      while (resultSet.next()) {
	        results.add(
	        		new ToDo(resultSet.getString("Task"), resultSet.getString("Category"))
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
  
  //---------------------------------------------------------------------------
  // SETTERS
  //---------------------------------------------------------------------------


  //---------------------------------------------------------------------------
  // OTHER METHODS
  //---------------------------------------------------------------------------

  // add a task
  public int addTask(String desc, String category) {
    int result = 0;

    // set parameters, then execute insertNewTask
    try {
      ToDo newTask = new ToDo(desc,category);
      insertNewTaskToTodoTable.setInt(1, newTask.getID());
      insertNewTaskToTodoTable.setString(2, newTask.getTask());
      insertNewTaskToTodoTable.setBoolean(3, newTask.isTaskDone());
      insertNewTaskToTodoTable.setDate(4, newTask.getDateAdded());
      
     
      // ADD TO CATEGORIES TABLE TOO
      insertNewTaskToCategoriesTable.setInt(1, newTask.getID());
      insertNewTaskToCategoriesTable.setString(2, newTask.getCategory());
      

      // insert the new entry; returns @ of rows updated
      result = insertNewTaskToTodoTable.executeUpdate();
      insertNewTaskToCategoriesTable.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      close();
    }

    return result;
  }

 


  // close the database connection
  public void close() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
} // end class ToDoQueries
