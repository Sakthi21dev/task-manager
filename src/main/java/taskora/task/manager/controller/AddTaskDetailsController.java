package taskora.task.manager.controller;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import taskora.task.manager.constants.TaskStatus;
import taskora.task.manager.constants.WarningMessage;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.service.TaskRepository;
import taskora.task.manager.utils.DateUtils;


public class AddTaskDetailsController {

	TaskRepository taskRepository = TaskRepository.getInstance();
	ObservableList<TaskDetails> task = taskRepository.getTasks();

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

	boolean closeOnsubmit;
	
	@FXML
	public void initialize() {
		
		String newTaskId = TaskRepository.getNewId();
		closeOnsubmit = false;
		
		startDate.setConverter(DateUtils.getConverter());
		endDate.setConverter(DateUtils.getConverter());
		status.getItems().addAll(TaskStatus.values());
		
		taskId.setText(newTaskId);
		status.setValue(TaskStatus.YET_TO_START);
		startDate.setValue(LocalDate.now());

		status.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (TaskStatus.COMPLETED.equals(newValue)) {
				endDate.setValue(LocalDate.now());
			}else {
				endDate.setValue(null);
			}
		});
	}

	public void setTaskDetails(TaskDetails newTask) {
		String taskDetailsId = newTask.getTaskId();
		TaskDetails thatTask = taskRepository.getById(taskDetailsId);
		closeOnsubmit = true;
		
		taskId.setText(taskDetailsId);
		taskName.setText(thatTask.getTaskName());
		status.setValue(newTask.getStatus());
		startDate.setValue(newTask.getStartDate());
		spendHours.setText(newTask.getSpendHours());
		endDate.setValue(newTask.getEndDate());
		
	}

	@FXML
	public void handleSubmit(ActionEvent event) {

		if (validateMadantoryInputs()) {

			String taskId = this.taskId.getText();
			TaskDetails thatTask = taskRepository.getById(taskId);
			TaskStatus taskStatus = status.getValue();

			if (thatTask != null) {
				newTask = thatTask;
			} else {
				task.add(newTask);
			}

			newTask.setTaskId(taskId);
			newTask.setTaskName(taskName.getText());
			newTask.setStatus(taskStatus);
			newTask.setStartDate(startDate.getValue());
			newTask.setSpendHours(spendHours.getText());

			if (TaskStatus.COMPLETED.equals(taskStatus)) {
				newTask.setEndDate(endDate.getValue());
			}

			if(closeOnsubmit) {
				Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	            stage.close();
			}
			
			handleClear();
		}

	}

	private boolean validateMadantoryInputs() {
		
		if(taskName.getText().trim().isEmpty()) {
			showAlert(WarningMessage.EMPTY_TASK_NAME.toString());
			return false;
		}
		
		if(TaskStatus.COMPLETED.equals(status.getValue())) {
			String taskSpendHours = spendHours.getText().trim();
			if(taskSpendHours.isEmpty()) {				
				showAlert(WarningMessage.EMPTY_SPEND_HOURS.toString());
				return false;
			}
			
			if(!DateUtils.isValidDuration(taskSpendHours)) {
				showAlert(WarningMessage.INVALID_SPEND_HOURS_FORMAT.toString());
				return false;
			}
		}
		
		return true;
		
	}
	
	 private void showAlert(String message) {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Warning");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait(); // Show the alert and wait for it to be closed
	    }


	@FXML
	public void handleClear() {
		taskName.clear();
		status.setValue(TaskStatus.YET_TO_START);
		startDate.setValue(LocalDate.now());
		endDate.setValue(null);
		spendHours.clear();
	}
}
