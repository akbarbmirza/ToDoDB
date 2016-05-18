import java.sql.*;
import java.util.List;

public class Main {

  public static void handleInput() {

  }

  public static void printMenu() {
    final String MENU =
            "=== MENU ===\n" +
                    "(1)\tSee Tasks\n" +
                    "(2)\tMark Task Done\n" +
                    "(3)\tAdd New Task\n";
    final String PROMPT = "Please Choose from the Menu:\n>\t";

    System.out.println(MENU);
    System.out.println(PROMPT);
  }

  public static void printList(List<ToDo> list) {
    for (ToDo item : list) {
      System.out.println(item);
    }
  }

  public static void main(String[] args) {

    printMenu();

//    ToDo myTask = new ToDo("Save the World!");
    System.out.printf("%3s\t%-15s\t%-15s\t%s\t%10s%n", "NUM", "TASK", "CATEGORY", "DONE", "DATE");
//    System.out.println(myTask);
//    myTask.markDone();
//    System.out.println(myTask);


    // SAMPLE CODE FROM PROFESSOR
    // Establish database connection
    String connectionString = "jdbc:hsqldb:testdb,sa,";
    Connection connection = null;
    try {
      Class.forName("org.hsqldb.jdbcDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      connection = DriverManager.getConnection(connectionString);
    } catch (SQLException e) {
      System.out.println(e);
    }

    // Run SQL queries
    Statement s;
    String sql;
    ToDoQueries tdq = new ToDoQueries();

    // DROP TABLE
//    sql = "DROP TABLE TODO;";
//    try {
//      s = connection.createStatement();
//      ResultSet rs = s.executeQuery(sql);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

//    // CREATE TABLE
//    sql = "CREATE TABLE TODO(ID INT, Task VARCHAR(255), Done BOOLEAN NOT NULL);";
//    try {
//      s = connection.createStatement();
//      ResultSet rs = s.executeQuery(sql);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

    // INSERT INTO GTable
//    try {
    // sql = "INSERT INTO TODO VALUES('1', 'Get DB Running', FALSE);";
    // s = connection.createStatement();
    // ResultSet rs = s.executeQuery(sql);

    tdq.addTask("Get DB Running","SCHOOL");
//    }
//    catch (SQLException e) {
//      e.printStackTrace();
//    }

    // INSERT INTO GTable (more)
//    try {
//      sql = "INSERT INTO TODO VALUES('2', 'Figure this out', FALSE);";
//      s = connection.createStatement();
//      ResultSet rs = s.executeQuery(sql);
    tdq.addTask("Figure This Out","GENERAL");
    tdq.addTask("E-mail Boss","WORK");
    tdq.addTask("Buy Fruits","HOME");
    tdq.addTask("Pay Phone Bill","PERSONAL");
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

//    // SELECT FROM TABLE
//    try {
//      sql = "SELECT * FROM TODO ORDER BY ID ASC;";
//      s = connection.createStatement();
//      ResultSet rs = s.executeQuery(sql);
//      while (rs.next()){
//        System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

//    // DELETE FROM GTable
//    try {
//      sql = "DELETE FROM TODO WHERE ID=2;";
//      s = connection.createStatement();
//      ResultSet rs = s.executeQuery(sql);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    // Close database connection
//    try {
//      connection.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

//    List<ToDo> myList = tdq.getAllTasksFromTodoTable();
//   printList(myList);
    
//    List<ToDo> myList1 = tdq.getAllTasksFromCategoriesTable();
//    printList(myList1);
      
    List<ToDo> myList1 = tdq.getAllTasks();
   	printList(myList1);

    GTable gui = new GTable();
    gui.runTable();
  }
}
