import java.sql.*;
import java.util.ArrayList;

/**
 * Based on Fig. 24.31: PersonQueries.java PreparedStatements used by the ToDo
 * Application.
 */

public class ToDoQueries {

	// ===========================================================================
	// FIELDS
	// ===========================================================================

	private static final String URL = "jdbc:hsqldb:testdb";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "";
	private ArrayList<ToDo> todoList;

	private Connection connection; // manages connection

	// Sql Statements For TODO GTable
	private PreparedStatement selectTaskByIDFromTodoTable;
	private PreparedStatement insertNewTaskToTodoTable;
	private PreparedStatement updateTaskByIDFromTodoTable;
	// - marking an item done - execute sql, and write function
	private PreparedStatement markTaskDoneByIDFromTodoTable;
	// Sql Statements For CATEGORIES GTable
	private PreparedStatement insertNewTaskToCategoriesTable;

	// See all columns together
	private PreparedStatement selectAllTasks;

	// ===========================================================================
	// CONSTRUCTOR
	// ===========================================================================

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

			// ************************* TODO GTable
			// ********************************

			// CREATE TODO TABLE
			sql = "CREATE TABLE TODO(ID INT, Task VARCHAR(255), Done BOOLEAN NOT NULL, Date DATE );";

			try {
				s = connection.createStatement();
				rs = s.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}

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
			sql = "UPDATE TODO SET Done = NOT Done WHERE ID = ?;";
			markTaskDoneByIDFromTodoTable = connection.prepareStatement(sql);

			// ******************* CATEGORIES TABLE
			// **************************************

			// CREATE SECOND TABLE
			sql = "CREATE TABLE CATEGORIES (ID INT, Category VARCHAR(255));";
			try {
				s = connection.createStatement();
				rs = s.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// create query that selects all entries from the CATEGORIES GTable
			sql = "SELECT TODO.ID, TODO.Task, CATEGORIES.Category, TODO.Done, TODO.Date FROM TODO INNER JOIN CATEGORIES ON "
					+ "TODO.ID=CATEGORIES.ID;";
			selectAllTasks = connection.prepareStatement(sql);

			// create insert that adds a new task into the database
			sql = "INSERT INTO CATEGORIES (ID, Category) VALUES (?, ?);";
			insertNewTaskToCategoriesTable = connection.prepareStatement(sql);

      // initialize our ArrayList
      this.todoList = getAllTasks();

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	// ===========================================================================
	// METHODS
	// ===========================================================================
	// GETTERS
	// ---------------------------------------------------------------------------

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

	// For inner joinof both of the tables
	public ArrayList<ToDo> getAllTasks() {
		ArrayList<ToDo> results = null;
		ResultSet resultSet = null;

		try {
			// executeQuery returns ResultSet containing matching entries
			resultSet = selectAllTasks.executeQuery();
			results = new ArrayList<ToDo>();

			while (resultSet.next()) {
				results.add(
						new ToDo(resultSet.getInt("ID"), resultSet.getString("Task"), resultSet.getString("Category")));

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

	public ArrayList<ToDo> getTodoList() {
		return todoList;
	}

	// ---------------------------------------------------------------------------
	// SETTERS
	// ---------------------------------------------------------------------------

	private void addToTodos(ToDo toAdd) {
		this.todoList.add(toAdd);
	}

	private void updateTodo(ToDo toUpdate) {
		this.todoList.set(toUpdate.getID() - 1, toUpdate);
	}

	// ---------------------------------------------------------------------------
	// OTHER METHODS
	// ---------------------------------------------------------------------------

	// add a task
	public void addTask(String desc, String category) {

		// set parameters, then execute insertNewTask
		try {
			ToDo newTask = new ToDo(desc, category);
			insertNewTaskToTodoTable.setInt(1, newTask.getID());
			insertNewTaskToTodoTable.setString(2, newTask.getTask());
			insertNewTaskToTodoTable.setBoolean(3, newTask.isTaskDone());
			insertNewTaskToTodoTable.setDate(4, newTask.getDateAdded());

			// ADD TO CATEGORIES TABLE TOO
			insertNewTaskToCategoriesTable.setInt(1, newTask.getID());
			insertNewTaskToCategoriesTable.setString(2, newTask.getCategory());

			// insert the new entry; returns @ of rows updated
			insertNewTaskToTodoTable.executeUpdate();
			insertNewTaskToCategoriesTable.executeUpdate();

			// add to our todoList
			addToTodos(newTask);
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}

		// return result;
	}

	public int editTask(int ID, String newDesc) {
		if (ID > getTodoList().size() || ID < 1) {
      System.out.println("Sorry, that task doesn't exist");
      return -1; // error
    }
    try {
      // Update task on our TD object
      getTodoList().get(ID - 1).editTask(newDesc);

			// Initialize our Prepared Statement
			updateTaskByIDFromTodoTable.setString(1, newDesc);
			updateTaskByIDFromTodoTable.setInt(2, ID);

			// Execute our statement
			updateTaskByIDFromTodoTable.executeUpdate();

			// Update our todoList
			// updateTodo(toEdit);
		} catch (SQLException e) {
			e.printStackTrace();
      return -1; // error
		}

    return 0; // success
	};

	// Mark Done as True in TODO GTable
  // marks done if done
  // marks not done if not done
	public int markDone(int ID) {
		if (ID > getTodoList().size() || ID < 1) {
			System.out.println("Sorry, that task doesn't exist");
      return -1; // error
		}
		try {
			markTaskDoneByIDFromTodoTable.setInt(1, ID);
			// Execute the statement
			markTaskDoneByIDFromTodoTable.executeUpdate();

			// update task on list
			getTodoList().get(ID - 1).markDone();
		} catch (SQLException e) {
			e.printStackTrace();
      return -1; // error
		}
    return 0; // success
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