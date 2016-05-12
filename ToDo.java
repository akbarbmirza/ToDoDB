import java.util.Date;

public class ToDo {

  //===========================================================================
  // FIELDS
  //===========================================================================
  private static int taskCount = 0;
  private int ID;
  private String taskDesc;
  private boolean isDone;
  private Date dueDate;
  private String category;

  //===========================================================================
  // CONSTRUCTOR
  //===========================================================================

  ToDo(String desc) {
    // Initialize our fields
    this.ID = ++taskCount;
    this.taskDesc = desc;
    this.isDone = false;
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

  //---------------------------------------------------------------------------
  // SETTERS
  //---------------------------------------------------------------------------

  private void editTask(String newDesc) {
    this.taskDesc = newDesc;
  }

  public void markDone() {
    this.isDone = true;
  }

  private void markNotDone() {
    this.isDone = false;
  }

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
    return String.format("%3d\t%-15s\t%s", this.getID(), this.getTask(), this.doneEmoji());
  }

}
