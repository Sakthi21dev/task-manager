package taskora.task.manager.constants;

public enum TaskStatus {

  YET_TO_START("Yet To Start"), IN_PROGRESS("In Progress"), HOLD("Hold"),
  RE_ASSIGNED("Re Assigned"), COMPLETED("Completed");

  String status;

  TaskStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return this.status;
  }
}
