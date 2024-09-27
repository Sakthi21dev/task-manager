package task.manager.application.controller;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import task.manager.application.constant.TaskStatus;
import task.manager.application.model.TaskDetails;
import task.manager.application.service.TaskRepository;

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

	@FXML
	public void initialize() {
		String newTaskId = TaskRepository.getNewId();
		taskId.setText(newTaskId);
		status.getItems().addAll(TaskStatus.values());
		status.setValue(TaskStatus.YET_TO_START);
		startDate.setValue(LocalDate.now());
//		endDate.setValue(LocalDate.now());
	}
	
	public void setTaskDetails(TaskDetails newTask) {
		String taskDetailsId = newTask.getTaskId();
		TaskDetails thatTask = taskRepository.getById(taskDetailsId);
		taskId.setText(taskDetailsId);
		taskName.setText(thatTask.getTaskName());
		status.getItems().addAll(TaskStatus.values());
		status.setValue(newTask.getStatus());
		startDate.setValue(newTask.getStartDate());
		spendHours.setText(newTask.getSpendHours());
		endDate.setValue(newTask.getEndDate());
	}

	@FXML
	public void handleOk(ActionEvent event) {
		String taskId = this.taskId.getText();
		TaskDetails thatTask = taskRepository.getById(taskId);
		
		if(thatTask != null) {
			 newTask = thatTask;
		}else {			
			task.add(newTask);
		}
		
		newTask.setTaskId(taskId);
		newTask.setTaskName(taskName.getText());
		newTask.setStatus(status.getValue());
		newTask.setStartDate(startDate.getValue());
		newTask.setEndDate(endDate.getValue());
		newTask.setSpendHours(spendHours.getText());
		
		handleCancel();
	}

	@FXML
	public void handleCancel() {
		taskName.clear();
		taskId.clear();
		taskId.setText(TaskRepository.getNewId());
		status.getItems().addAll(TaskStatus.values());
		status.setValue(TaskStatus.YET_TO_START);
		startDate.setValue(LocalDate.now());
//		endDate.setValue(LocalDate.now());
		spendHours.clear();
	}
}
