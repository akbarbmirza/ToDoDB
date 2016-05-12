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
  private PreparedStatement selectAllTasks;
  private PreparedStatement selectTaskByID;
  private PreparedStatement insertNewTask;

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

      // DROP TABLE
      sql = "DROP TABLE TODO;";
//    try {
      s = connection.createStatement();
      rs = s.executeQuery(sql);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

      // CREATE TABLE
      sql = "CREATE TABLE TODO(ID INT, Task VARCHAR(255), Done BOOLEAN NOT NULL);";
//      try {
      s = connection.createStatement();
//        ResultSet rs = s.executeQuery(sql);
      rs = s.executeQuery(sql);
//      } catch (SQLException e) {
//        e.printStackTrace();
//      }

      // create query that selects all entries in the ToDo List
      sql = "SELECT * FROM TODO";
      selectAllTasks = connection.prepareStatement(sql);

      // create query that selects entries with a specific ID
      sql = "SELECT * FROM TODO WHERE ID = ?";
      selectTaskByID = connection.prepareStatement(sql);

      // create insert that adds a new task into the database
      sql = "INSERT INTO TODO (ID, Task, Done) VALUES (?, ?, ?)";
      insertNewTask = connection.prepareStatement(sql);

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
  public List<ToDo> getAllTasks() {
    List<ToDo> results = null;
    ResultSet resultSet = null;

    try {
      // executeQuery returns ResultSet containing matching entries
      resultSet = selectAllTasks.executeQuery();
      results = new ArrayList<ToDo>();

      while (resultSet.next()) {
        results.add(
                new ToDo(resultSet.getString("Task"))
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
  public int addTask(String desc) {
    int result = 0;

    // set parameters, then execute insertNewTask
    try {
      ToDo newTask = new ToDo(desc);
      insertNewTask.setInt(1, newTask.getID());
      insertNewTask.setString(2, newTask.getTask());
      insertNewTask.setBoolean(3, newTask.isTaskDone());

      // insert the new entry; returns @ of rows updated
      result = insertNewTask.executeUpdate();
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
