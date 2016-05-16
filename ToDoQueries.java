import javax.xml.transform.Result;
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
  // TODO: Write Prepared Statement for picking one item
  private PreparedStatement selectTaskByIDFromTodoTable;
  private PreparedStatement insertNewTaskToTodoTable;
  // TODO: Write Prepared Statement for editing one item
  private PreparedStatement updateTaskByIDFromTodoTable;

  // Aziz - Tasks
  // - marking an item done - execute sql, and write function
  // TODO: Write Prepared Statement for marking an item done
  private PreparedStatement markTaskDoneByIDFromTodoTable;
  // - deleting an item, removing it from sql database
  // - creating a User class w/ username, password, tasks array that holds ids of tasks they own
  // TODO: Write Prepared Statement for deleting one item
  private PreparedStatement deleteTaskByIDFromTodoTable;


  //Sql Statements For CATEGORIES Table
  private PreparedStatement selectAllTasksFromCategoriesTable;
  private PreparedStatement selectTaskByIDFromCategoriesTable;
  private PreparedStatement insertNewTaskToCategoriesTable;
  private PreparedStatement getDeleteTaskByIDFromCategoriesTable;
  
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

      // DROP TODO TABLE
      sql = "DROP TABLE TODO;";
      try {
        s = connection.createStatement();
        rs = s.executeQuery(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      // DROP CATEGORIES TABLE
      sql = "DROP TABLE CATEGORIES;";
      try {
        s = connection.createStatement();
        rs = s.executeQuery(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      }

 
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
      sql = "SELECT * FROM TODO;";
      selectAllTasksFromTodoTable = connection.prepareStatement(sql);

      // create query that selects entries with a specific ID
      sql = "SELECT * FROM TODO WHERE ID = ?;";
      selectTaskByIDFromTodoTable = connection.prepareStatement(sql);

      // create insert that adds a new task into the database
      sql = "INSERT INTO TODO (ID, Task, Done, Date) VALUES (?, ?, ?, ?);";
      insertNewTaskToTodoTable = connection.prepareStatement(sql);

      // create query that updates the given task in the database
      sql = "UPDATE TODO SET Task = ? WHERE ID = ?;";
      updateTaskByIDFromTodoTable = connection.prepareStatement(sql);

      // create query that marks task as done in the database
      sql = "UPDATE TODO SET Done = TRUE WHERE ID = ?;";
      markTaskDoneByIDFromTodoTable = connection.prepareStatement(sql);

      
  // ******************* CATEGORIES TABLE ************************************** 

      // CREATE SECOND TABLE
      sql = "CREATE TABLE CATEGORIES (ID INT, Category VARCHAR(255));";
      try {
        s = connection.createStatement();
        rs = s.executeQuery(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      // create query that selects all entries from the CATEGORIES Table
      sql = "SELECT TODO.ID, TODO.Task, CATEGORIES.Category, TODO.Done, TODO.Date FROM TODO INNER JOIN CATEGORIES ON " +
              "TODO.ID=CATEGORIES.ID;";
      selectAllTasks = connection.prepareStatement(sql);

      sql = "SELECT * FROM CATEGORIES;";
      selectAllTasksFromCategoriesTable = connection.prepareStatement(sql);

      // create query that selects entries with a specific ID
      sql = "SELECT * FROM CATEGORIES WHERE ID = ?;";
      selectTaskByIDFromCategoriesTable = connection.prepareStatement(sql);

      // create insert that adds a new task into the database
      sql = "INSERT INTO CATEGORIES (ID, Category) VALUES (?, ?);";
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

  public ToDo getTaskByIDFromTodoTable(int ID) {
    ToDo results = null;
    ResultSet resultSet = null;
    try {
      resultSet = selectTaskByIDFromTodoTable.executeQuery();
      while (resultSet.next()) {
        results = new ToDo(resultSet.getString("Task"), null);
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
  // TODO: make this function a void function
  public void addTask(String desc, String category) {
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

    // return result;
  }

  public void editTask(ToDo toEdit, String newDesc) {
    try {
      // Update Category on our TD object
      toEdit.editTask(newDesc);

      // Initialize our Prepared Statement
      updateTaskByIDFromTodoTable.setString(1, newDesc);
      updateTaskByIDFromTodoTable.setInt(2, toEdit.getID());

      // Execute our statement
      updateTaskByIDFromTodoTable.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  };
 


  // close the database connection
  public void close() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
} // end class ToDoQueries
