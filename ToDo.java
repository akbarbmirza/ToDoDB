public class ToDo {

	// ===========================================================================
	// FIELDS
	// ===========================================================================
	private static int taskCount = 0;			// To keep track how many tasks are being added
	private int ID;								// ID of the task
	private String taskDesc;					// Task Name 
	private boolean isDone;						// Boolean to keep track of which task is done and which is not
	private java.sql.Date dateAdded;			// Date when the task was added
	private String category;					// Category of the task

	// ===========================================================================
	// CONSTRUCTOR
	// ===========================================================================
	
	// First Constructor which takes task name and category as parameters
	ToDo(String desc, String category) {
		// Initialize our fields
		this.ID = ++taskCount;			// Increment ID every time a task is added ( To have an unique ID )
		this.taskDesc = desc;			// Setting task name 
		this.isDone = false;			// Setting the task not done initially
		this.dateAdded = new java.sql.Date(new java.util.Date().getTime());			// Setting date provided by current date from Java library
		
		// If category is null set it to "N/A"( Not Available ) else, set the category
		if (category == null) {					
			this.category = "N/A";
		} else {
			this.category = category;
		}

	}

	// Second Constructor which takes ID, task name and Category as parameters
	ToDo(int ID, String desc, String category) {
		// Initialize Fields
		this.ID = ID;				// Setting the ID got from parameter
		this.taskDesc = desc;		// Setting task name
		this.isDone = false;		// Setting the task not done initially
		this.dateAdded = new java.sql.Date(new java.util.Date().getTime());			// Setting date provided by current date form Java library
		
		// If category is null set it to "N/A"( Not Available ) else, set the category
		if (category == null) {
			this.category = "N/A";
		} else {
			this.category = category;
		}

	}

	// ===========================================================================
	// METHODS
	// ===========================================================================
	// GETTERS
	// ---------------------------------------------------------------------------

	// Getting ID
	public int getID() {
		return this.ID;
	}
	
	// Getting Task name
	public String getTask() {
		return this.taskDesc;
	}
	
	// Getting if the task is done or not done
	public boolean isTaskDone() {
		return this.isDone;
	}

	// Getting the add when the task was added
	public java.sql.Date getDateAdded() {
		return this.dateAdded;
	}

	// Getting the category of the task
	public String getCategory() {
		return this.category;
	}

	// ---------------------------------------------------------------------------
	// SETTERS
	// ---------------------------------------------------------------------------

	// Editing the task name
	public void editTask(String newDesc) {
		this.taskDesc = newDesc;
	}

	// Marking the task done
	public void markDone() {
		this.isDone = !this.isDone;
	}



	// ---------------------------------------------------------------------------
	// OTHER METHODS
	// ---------------------------------------------------------------------------
	
	// Set the emoji on console depending if the task is done or not
	private String doneEmoji() {
		if (this.isTaskDone()) {
			return "[✔]";
		}
		return "[ ]";
	}

	// Conversion of Todo to String ( For Console Control )
	@Override
	public String toString() {
		return String.format("%3s\t%-15s\t%-15s\t%-10s\t%10s", this.getID(), this.getTask(), this.getCategory(),
				this.doneEmoji(), this.getDateAdded());
	}

}