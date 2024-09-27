package task.manager.application.controller;

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
import task.manager.application.constant.TaskStatus;
import task.manager.application.constant.WarningMessage;
import task.manager.application.model.TaskDetails;
import task.manager.application.service.TaskRepository;
import task.manager.application.utils.DateUtils;

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
		taskId.setText(newTaskId);
		status.getItems().addAll(TaskStatus.values());
		status.setValue(TaskStatus.YET_TO_START);
		startDate.setValue(LocalDate.now());
		status.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (TaskStatus.COMPLETED.equals(newValue)) {
				endDate.setValue(LocalDate.now());
			}else {
				endDate.setValue(null);
			}
		});
		closeOnsubmit = false;
//		endDate.setValue(LocalDate.now());
	}

	public void setTaskDetails(TaskDetails newTask) {
		String taskDetailsId = newTask.getTaskId();
		TaskDetails thatTask = taskRepository.getById(taskDetailsId);
		taskId.setText(taskDetailsId);
		taskName.setText(thatTask.getTaskName());
//		status.getItems().addAll(TaskStatus.values());
		status.setValue(newTask.getStatus());
		startDate.setValue(newTask.getStartDate());
		spendHours.setText(newTask.getSpendHours());
		endDate.setValue(newTask.getEndDate());
		closeOnsubmit = true;
	}

	@FXML
	public void handleOk(ActionEvent event) {

		if (validateMadantoryInputs()) {

			String taskId = this.taskId.getText();
			TaskDetails thatTask = taskRepository.getById(taskId);

			if (thatTask != null) {
				newTask = thatTask;
			} else {
				task.add(newTask);
			}

			newTask.setTaskId(taskId);
			newTask.setTaskName(taskName.getText());
			TaskStatus taskStatus = status.getValue();
			newTask.setStatus(taskStatus);
			newTask.setStartDate(startDate.getValue());
			if (TaskStatus.COMPLETED.equals(taskStatus)) {
				newTask.setEndDate(endDate.getValue());
			}
			newTask.setSpendHours(spendHours.getText());
			if(closeOnsubmit) {
				Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	            stage.close();
			}
			handleCancel();
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
	public void handleCancel() {
		taskName.clear();
//		taskId.clear();
//		taskId.setText(TaskRepository.getNewId());
		status.setValue(TaskStatus.YET_TO_START);
		startDate.setValue(LocalDate.now());
		endDate.setValue(null);
		spendHours.clear();
	}
}
