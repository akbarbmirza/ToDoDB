import java.sql.*;
import java.util.InputMismatchException;
import java.util.List;
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

	public static void addTask(Scanner in, ToDoQueries tdq) {
		String task, category, line;

		// Print Instructions for Adding Task
		System.out.println("--- Add a Task ---");
		System.out.print("What do you need to do? Type q() to Cancel\n> ");
		if (in.hasNext()) {
			line = in.nextLine();
			if (!line.contains("q()")) {
				task = line;
				System.out.println("What category is this task?\n> ");
				category = in.nextLine();
				tdq.addTask(task, category);
				System.out.printf("Your task '%s' has been added!%n", task);
			}
		}
	}

	public static void markDone(Scanner in, ToDoQueries tdq) {
		String line;
		int ID;
		System.out.println("--- Mark Task Done ---");
		printList(tdq.getTodoList());
		System.out.print("What do you want to mark done? Type q() to Cancel\n> ");
		if (in.hasNext()) {
			line = in.nextLine();
			if (!line.contains("q()")) {
				ID = Integer.parseInt(line);
				tdq.markDone(ID);
				System.out.printf("TASK %d has been marked done!%n", ID);
			}
		}
	}

	public static void printMenu() {
		final String MENU = "=== MENU ===\n" + "1:\tSee Tasks\n" + "2:\tMark Task Done\n" + "3:\tAdd New Task\n"
            + "4:\tEdit a Task\n" + "5:\tQuit\n";
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

		System.out.printf("%3s\t%-15s\t%-15s\t%s\t%10s%n", "NUM", "TASK", "CATEGORY", "DONE", "DATE");

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

		List<ToDo> myList1 = tdq.getTodoList();

		GTable gui = new GTable(tdq);
		gui.runTable();

		while (true) {
			printList(myList1);
			printMenu();
			handleInput(tdq);
		}

		// GTable gui = new GTable();
		// gui.runTable();
	}
}