package taskora.task.manager.controller;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import taskora.task.manager.constants.TaskStatus;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.service.TaskDetailService;
import taskora.task.manager.service.ValidationService;
import taskora.task.manager.utils.DateUtils;

public class AddTaskDetailsController {

  TaskDetailService taskRepository = TaskDetailService.getInstance();
  ObservableList<TaskDetails> task = taskRepository.getTasks();

  @FXML
  TextField id;
  
  @FXML
  TextField taskId;

  @FXML
  TextField taskName;

  @FXML
  ChoiceBox<TaskStatus> status;

  @FXML
  DatePicker startDate;

  @FXML
  DatePicker endDate;

  @FXML
  TextField spendHours;

  TaskDetails newTask = new TaskDetails();

  boolean onEditMode;

  @FXML
  public void handleClear() {

    if (!onEditMode) {
      taskId.clear();
    }
    taskName.clear();
    status.setValue(TaskStatus.YET_TO_START);
    startDate.setValue(LocalDate.now());
    endDate.setValue(null);
    spendHours.clear();
  }

  @FXML
  public void handleSubmit(ActionEvent event) {

    if (validateMadantoryInputs()) {

      String taskId = this.taskId.getText();
//      TaskDetails thatTask = taskRepository.getById(taskId);
//      if (thatTask != null) {
//        newTask = thatTask;
//      }

      TaskStatus taskStatus = status.getValue();

      newTask.setTaskId(taskId);
      newTask.setTaskName(taskName.getText());
      newTask.setStatus(taskStatus);
      newTask.setStartDate(startDate.getValue());
      newTask.setSpendHours(spendHours.getText());

      if (TaskStatus.COMPLETED.equals(taskStatus)) {
        newTask.setEndDate(endDate.getValue());
      }

      taskRepository.saveTask(newTask);

      if (onEditMode) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
      }

      handleClear();
    }

  }

  @FXML
  public void initialize() {

//		String newTaskId = TaskRepository.getNewId();
    onEditMode = false;
    taskId.setEditable(true);

    startDate.setConverter(DateUtils.getConverter());
    endDate.setConverter(DateUtils.getConverter());
    status.getItems().addAll(TaskStatus.values());

//		taskId.setText(newTaskId);
    status.setValue(TaskStatus.YET_TO_START);
    startDate.setValue(LocalDate.now());

    /*
     * While Changing status to TaskStatus.COMPLETED set end date as today.
     */
    status.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (TaskStatus.COMPLETED.equals(newValue)) {
            endDate.setValue(LocalDate.now());
          } else {
            endDate.setValue(null);
          }
        });
  }

  public void setTaskDetails(TaskDetails newTask) {
    String id = newTask.getId();
    TaskDetails thatTask = taskRepository.getById(id);
    this.onEditMode = true;
    this.id.setText(id);
    this.taskId.setText(thatTask.getTaskId());
    this.taskName.setText(thatTask.getTaskName());
    this.status.setValue(thatTask.getStatus());
    this.startDate.setValue(thatTask.getStartDate());
    this.spendHours.setText(thatTask.getSpendHours());
    this.endDate.setValue(thatTask.getEndDate());

  }

  private boolean validateMadantoryInputs() {

    if (ValidationService.validateTaskName(this.taskName)
        && ValidationService.validateSpendHours(status, spendHours)) {
      return true;
    }
    return false;

  }
}
