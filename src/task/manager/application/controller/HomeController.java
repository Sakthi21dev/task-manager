package task.manager.application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import task.manager.application.model.TaskDetails;

public class HomeController {
	
	@FXML
	BorderPane rootPane;
		
	@FXML
	private void handleAddTask(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/task/manager/application/view/AddTaskDetails.fxml"));
		GridPane taskDetailsLayout = loader.load();
		rootPane.setCenter(taskDetailsLayout);
	}

	@FXML
	private void goHome(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/task/manager/application/view/TaskOfTheDayLayout.fxml"));
		AnchorPane taskDetailsLayout = loader.load();
		rootPane.setCenter(taskDetailsLayout);
	}
	
	@FXML
	private void goReport(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/task/manager/application/view/TaskOfTheDayLayout.fxml"));
		AnchorPane taskDetailsLayout = loader.load();
		rootPane.setCenter(taskDetailsLayout);
	}	
	
	public BorderPane getRoot(){
		return rootPane;
	}
}
