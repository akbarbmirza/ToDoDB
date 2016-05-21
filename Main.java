import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void handleInput(ToDoQueries tdq) {
		// create a scanner to take in input
		Scanner in = new Scanner(System.in);
		try {
			String line = in.nextLine();
			int choice;
			try {
				choice = Integer.parseInt(line);
			} catch (NumberFormatException ex) {
				choice = 0;
			}

			switch (choice) {
			case 1:
				// Show Tasks Menu
        showTasks(tdq);
				break;
			case 2:
				// Mark Task Done
				markDone(in, tdq);
				break;
			case 3:
				// Add Task
				addTask(in, tdq);
				break;
			case 4:
				// Edit Task
        editTask(in, tdq);
				break;
			case 5:
				// Quit
				System.exit(0);
				break;
			default:
				System.out.println("That number is not on the menu. Please try one on the menu");
			}
		} catch (InputMismatchException e) {
			System.out.println("Your input is invalid. Please enter a number on the menu");
		}
	}

  public static void showTasks(ToDoQueries tdq) {
    ArrayList<ToDo> list = tdq.getTodoList();
    System.out.printf("%3s\t%-15s\t%-15s\t%s\t%10s%n", "NUM", "TASK", "CATEGORY", "DONE", "DATE");
    printList(list);
    System.out.println();
  }

	public static void addTask(Scanner in, ToDoQueries tdq) {
		String task, category, line;

		// Print Instructions for Adding Task
		System.out.println("--- Add a Task ---");
		System.out.print("What do you need to do? Type q() to Cancel\n>\t");
		if (in.hasNext()) {
			line = in.nextLine();
			if (!line.contains("q()")) {
				task = line;
				System.out.println("What category is this task?\n>\t");
				category = in.nextLine();
				tdq.addTask(task, category);
				System.out.printf("Your task '%s' has been added!%n", task);
			}
		}
	}

  public static void editTask(Scanner in, ToDoQueries tdq) {
    int ID;
    String task, line;

    // Print Instructions for Editing a Task
    System.out.println("--- Edit a Task ---");
    System.out.print("What task do you want to edit? Type q() to Cancel\n>\t");
    if (in.hasNext()) {
      line = in.nextLine();
      if (!line.contains("q()")) {
        ID = Integer.parseInt(line);
        System.out.println("What do you want to change the task to?\n>\t");
        task = in.nextLine();
        if (tdq.editTask(ID, task) != -1)
          System.out.printf("Task %d was edited!%n", ID);
        else
          System.out.printf("Task %d could not be edited!%n", ID);
      }
    }
  }

	public static void markDone(Scanner in, ToDoQueries tdq) {
		String line;
		int ID;
		System.out.println("--- Toggle Task Done/Not Done ---");
//		printList(tdq.getTodoList());
		System.out.print("What do you want to mark done/not done? Type q() to Cancel\n>\t");
		if (in.hasNext()) {
			line = in.nextLine();
			if (!line.contains("q()")) {
				ID = Integer.parseInt(line);
				if (tdq.markDone(ID) != -1)
				  System.out.printf("TASK %d status has changed!%n", ID);
        else
          System.out.printf("TASK %d could not be toggled!%n", ID);
			}
		}
	}

	public static void printMenu() {
		final String MENU = "=== MENU ===\n" + "1:\tSee Tasks\n" + "2:\tToggle Task Done/Not Done\n" + "3:\tAdd New Task\n"
            + "4:\tEdit a Task\n" + "5:\tQuit\n";
		final String PROMPT = "Please Choose from the Menu:\n>\t";

		System.out.println(MENU);
		System.out.print(PROMPT);
	}

	public static void printList(ArrayList<ToDo> list) {
		for (ToDo item : list) {
			System.out.println(item);
		}
	}

	public static void main(String[] args) {

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

		tdq.addTask("Get DB Running", "SCHOOL");
		tdq.addTask("Figure This Out", "GENERAL");
		tdq.addTask("E-mail Boss", "WORK");
		tdq.addTask("Buy Fruits", "HOME");
		tdq.addTask("Pay Phone Bill", "PERSONAL");

//		List<ToDo> myList1 = tdq.getTodoList();

		GTable gui = new GTable(tdq);
		gui.runTable();

		while (true) {
//			printList(myList1);
			printMenu();
			handleInput(tdq);
			gui.runTable();
			
		}

		// GTable gui = new GTable();
		// gui.runTable();
	}
}