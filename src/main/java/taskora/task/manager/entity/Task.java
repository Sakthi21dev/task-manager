package taskora.task.manager.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import taskora.task.manager.constants.TaskStatus;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.utils.LoggingUtil;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "task_id", nullable = false)
  private String taskId;

  @Column(name = "task_name", nullable = false)
  private String taskName;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "spend_hours")
  private String spendHours;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private TaskStatus status;

  // Constructors
  public Task() {
  }

  public Task(String id, String taskId, String taskName, LocalDate assignedDate,
      LocalDate startDate, LocalDate endDate, String estimatedHours, String spendHours,
      TaskStatus status) {

    this.id = Long.parseLong(id);
    this.taskId = taskId;
    this.taskName = taskName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.spendHours = spendHours;
    this.status = status;
  }

  public Task(TaskDetails newTaskDetail) {

    String taskId = newTaskDetail.getId();
    if (taskId != null && !taskId.isEmpty()) {
      this.id = Long.parseLong(taskId);
    }
    setTaskId(newTaskDetail.getTaskId());
    setTaskName(newTaskDetail.getTaskName());
    setStartDate(newTaskDetail.getStartDate());
    setSpendHours(newTaskDetail.getSpendHours());
    setEndDate(newTaskDetail.getEndDate());
    setStatus(newTaskDetail.getStatus());

  }

  public LocalDate getEndDate() {
    return endDate;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public String getSpendHours() {
    return spendHours;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public String getTaskId() {
    return taskId;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setSpendHours(String spendHours) {
    this.spendHours = spendHours;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", taskId=" + taskId + ", taskName=" + taskName + ", startDate="
        + startDate + ", endDate=" + endDate + ", spendHours=" + spendHours + ", status=" + status
        + "]";
  }

}
