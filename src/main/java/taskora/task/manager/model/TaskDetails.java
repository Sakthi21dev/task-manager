package taskora.task.manager.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import taskora.task.manager.constants.TaskStatus;

public class TaskDetails {

  private final StringProperty id = new SimpleStringProperty("");
  private final StringProperty taskId = new SimpleStringProperty("");
  private final StringProperty taskName = new SimpleStringProperty("");
  private final ObjectProperty<LocalDate> assignedDate = new SimpleObjectProperty<>();
  private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
  private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
  private final StringProperty estimatedHours = new SimpleStringProperty("");
  private final StringProperty spendHours = new SimpleStringProperty("");
  private final ObjectProperty<TaskStatus> status = new SimpleObjectProperty<>();

  public TaskDetails() {

  }

  public TaskDetails(Task task) {

    setId(String.valueOf(task.getId())  == null ? "" : String.valueOf(task.getId())  );
    setTaskId(task.getTaskId());
    setTaskName(task.getTaskName());
    setStartDate(task.getStartDate());
    setSpendHours(task.getSpendHours());
    setStatus(task.getStatus());
    setEndDate(task.getEndDate());

  }

  public StringProperty taskId() {
    return taskId;
  }

  public String getTaskId() {
    return taskId.get();
  }

  public void setTaskId(String taskId) {
    this.taskId.set(taskId);
  }

  public StringProperty taskName() {
    return taskName;
  }

  public String getTaskName() {
    return taskName.get();
  }

  public void setTaskName(String taskName) {
    this.taskName.set(taskName);
  }

  public ObjectProperty<LocalDate> assignedDate() {
    return assignedDate;
  }

  public LocalDate getAssignedDate() {
    return assignedDate.get();
  }

  public void setAssignedDate(LocalDate assignedDate) {
    this.assignedDate.set(assignedDate);
  }

  public ObjectProperty<LocalDate> startDate() {
    return startDate;
  }

  public LocalDate getStartDate() {
    return startDate.get();
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate.set(startDate);
  }

  public ObjectProperty<LocalDate> endDate() {
    return endDate;
  }

  public LocalDate getEndDate() {
    return endDate.get();
  }

  public void getEndDate(LocalDate endDate) {
    this.endDate.set(endDate);
  }

  public StringProperty estimatedHours() {
    return estimatedHours;
  }

  public String getEstimatedHours() {
    return estimatedHours.get();
  }

  public ObjectProperty<TaskStatus> status() {
    return status;
  }

  public StringProperty spendHours() {
    return spendHours;
  }

  public String getSpendHours() {
    return spendHours.get();
  }

  public void setSpendHours(String hours) {
    spendHours.set(hours);
  }

  public TaskStatus getStatus() {
    return status.get();
  }

  public void setStatus(TaskStatus taskDetails) {
    status.set(taskDetails);
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate.set(endDate);
  }

  public void setEstimatedHours(String hours) {
    this.estimatedHours.set(hours);
  }

  public StringProperty idProperty() {
    return this.id;
  }

  public String getId() {
    return this.idProperty().get();
  }

  public void setId(final String id) {
    this.idProperty().set(id);
  }

  @Override
  public String toString() {
    return "TaskDetails [id=" + id + ", taskId=" + taskId + ", taskName=" + taskName
        + ", assignedDate=" + assignedDate + ", startDate=" + startDate + ", endDate=" + endDate
        + ", estimatedHours=" + estimatedHours + ", spendHours=" + spendHours + ", status=" + status
        + "]";
  }

}
