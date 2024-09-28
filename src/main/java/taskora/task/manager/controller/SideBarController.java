package taskora.task.manager.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import taskora.task.manager.App;

public class SideBarController {

	@FXML
	private void handleAddTask(ActionEvent event) throws IOException {
		App.setRoot("AddTaskDetails");

	}

	@FXML
	private void goHome(ActionEvent event) throws IOException {
		App.setRoot("TaskOfTheDayLayout");
	}

//	@FXML
//	private void goReport(ActionEvent event) throws IOException {
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(getClass().getResource("/task/manager/application/view/TaskOfTheDayLayout.fxml"));
//		AnchorPane taskDetailsLayout = loader.load();
//		rootPane.setCenter(taskDetailsLayout);
//	}	

}
