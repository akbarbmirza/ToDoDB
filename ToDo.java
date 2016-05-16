public class ToDo {

  //===========================================================================
  // FIELDS
  //===========================================================================
  private static int taskCount = 0;
  private int ID;
  private String taskDesc;
  private boolean isDone;
  private java.sql.Date dateAdded;
  private String category;

  //===========================================================================
  // CONSTRUCTOR
  //===========================================================================

  ToDo(String desc, String category) {
    // Initialize our fields
    this.ID = ++taskCount;
    this.taskDesc = desc;
    this.isDone = false;
    this.dateAdded = new java.sql.Date(new java.util.Date().getTime());
    if ( category == null) {
    	this.category = "N/A";
    }
    else {
    	this.category = category;
    }
    
  }

  //===========================================================================
  // METHODS
  //===========================================================================
  // GETTERS
  //---------------------------------------------------------------------------

  public int getID() {
    return this.ID;
  }

  public String getTask() {
    return this.taskDesc;
  }

  public boolean isTaskDone() {
    return this.isDone;
  }

  public java.sql.Date getDateAdded() {
    return this.dateAdded;
  }
  
  public String getCategory() {
	  return this.category;
  }

  //---------------------------------------------------------------------------
  // SETTERS
  //---------------------------------------------------------------------------

  private void editTask(String newDesc) {
    this.taskDesc = newDesc;
  }
  
  private void editCategory(String newCategory)
  {
	  this.category = newCategory;
  }

  public void markDone() {
    this.isDone = true;
  }

  private void markNotDone() {
    this.isDone = false;
  }

//  public void setDateAdded(int year, int month, int date) {
//    this.dateAdded = new Date();
//  }

  //---------------------------------------------------------------------------
  // OTHER METHODS
  //---------------------------------------------------------------------------

  private String doneEmoji() {
    if (this.isTaskDone()) {
      return "[✔]";
    }
    return "[ ]";
  }

  @Override
  public String toString() {
    return String.format("%3s\t%-15s\t%-15s\t%s\t%10s", this.getID(), this.getTask(), this.getCategory(), this.doneEmoji(), this.getDateAdded());
  }

}
