package task.manager.application.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import task.manager.application.constant.TaskStatus;
import task.manager.application.model.TaskDetails;
import task.manager.application.service.TaskRepository;

public class RootLayoutController {
	
	BorderPane rootPane;

	@FXML
	private TableView<TaskDetails> taskTableView;

	@FXML
	private TableColumn<TaskDetails, String> taskId;

	@FXML
	private TableColumn<TaskDetails, String> taskName;

	@FXML
	private TableColumn<TaskDetails, TaskStatus> status;

	@FXML
	private TableColumn<TaskDetails, LocalDate> assignedDate;

	@FXML
	private TableColumn<TaskDetails, LocalDate> startDate;

	@FXML
	private TableColumn<TaskDetails, LocalDate> endDate;

	@FXML
	private TableColumn<TaskDetails, String> estimatedHours;

	@FXML
	private TableColumn<TaskDetails, String> spendHours;

	@FXML
	public void initialize() {

		taskId.setCellValueFactory(cellDate -> cellDate.getValue().taskId());
		taskName.setCellValueFactory(cellDate -> cellDate.getValue().taskName());
		status.setCellValueFactory(cellDate -> cellDate.getValue().status());
		startDate.setCellValueFactory(cellDate -> cellDate.getValue().startDate());
		endDate.setCellValueFactory(cellDate -> cellDate.getValue().endDate());
		spendHours.setCellValueFactory(cellDate -> cellDate.getValue().spendHours());
		
		taskTableView.setRowFactory( rowValue -> {
			   TableRow<TaskDetails> row = new TableRow<>();
			   row.setOnMouseClicked(event -> {
	                if (!row.isEmpty()) {
	                	TaskDetails rowData = row.getItem();
	                	try {
							goAddTaskOnEditMode(rowData, event);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            });
	            return row;
	        });
		
		
		taskTableView.setItems(TaskRepository.getInstance().getTasks());

	}
	
	private void goAddTaskOnEditMode(TaskDetails taskDetails, MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/task/manager/application/view/AddTaskDetails.fxml"));
		GridPane taskDetailsLayout = loader.load();
		AddTaskDetailsController controller = loader.getController();
		controller.setTaskDetails(taskDetails);
		Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Task");
        dialogStage.initModality(Modality.WINDOW_MODAL);
//        dialogStage.initOwner(taskTable.getScene().getWindow());
        Scene scene = new Scene(taskDetailsLayout);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
//		setRoot();
//		rootPane.setCenter(taskDetailsLayout);
	}
	
//	public void setRoot() {
//		rootPane = HomeController.getRoot();
//	}

}
