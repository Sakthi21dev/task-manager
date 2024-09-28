package task.manager.application.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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
	private TextField searchBox;

	@FXML
	public void initialize() {

		taskId.setCellValueFactory(cellDate -> cellDate.getValue().taskId());
		taskName.setCellValueFactory(cellDate -> cellDate.getValue().taskName());
		taskName.setCellFactory(TextFieldTableCell.forTableColumn()); // Enable editing for name column
		status.setCellValueFactory(cellDate -> cellDate.getValue().status());
		startDate.setCellValueFactory(cellDate -> cellDate.getValue().startDate());
		endDate.setCellValueFactory(cellDate -> cellDate.getValue().endDate());
		spendHours.setCellValueFactory(cellDate -> cellDate.getValue().spendHours());

		FilteredList<TaskDetails> filteredData = new FilteredList<>(TaskRepository.getInstance().getTasks(), b -> true);

		addAndShowContextMenu();

		taskName.setOnEditCommit(event -> {
			TaskDetails taskDetail = event.getRowValue();
			taskDetail.setTaskName(event.getNewValue());
		});

		doFilter(filteredData);

		taskTableView.setEditable(true);
		taskName.setEditable(true);
		taskTableView.setItems(filteredData);

	}

	private void addAndShowContextMenu() {
		ContextMenu contextMenu = addContextMenu();

		taskTableView.setRowFactory(rowValue -> {
			TableRow<TaskDetails> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getButton().name().equals("SECONDARY")) {
					contextMenu.show(row, event.getScreenX(), event.getScreenY());
				}
			});
			return row;
		});
	}

	private void doFilter(FilteredList<TaskDetails> filteredData) {
		searchBox.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				filteredData.setPredicate((task) -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String lowerCaseFilter = newValue.toLowerCase();

					if (TaskRepository.predicate(task, lowerCaseFilter)) {
						return true;
					}

					return false;
				});

			}
		});
	}

	private ContextMenu addContextMenu() {
		
		ContextMenu contextMenu = new ContextMenu();
		
		MenuItem editMenuItem = new MenuItem("Edit Task");
		MenuItem delteMenuItem = new MenuItem("Delete Task");
		MenuItem copyMenuItem = new MenuItem("Copy Task");
		
		
		editMenuItem.setOnAction(event -> {
			if (!taskTableView.getSelectionModel().isEmpty()) {
				TaskDetails selectedTask = taskTableView.getSelectionModel().getSelectedItem();
				try {
					goAddTaskOnEditMode(selectedTask, event);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		contextMenu.getItems().add(editMenuItem);
		return contextMenu;
	}

	private void goAddTaskOnEditMode(TaskDetails taskDetails, Event event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/task/manager/application/view/AddTaskDetails.fxml"));
		GridPane taskDetailsLayout = loader.load();
		Scene scene = new Scene(taskDetailsLayout);
		AddTaskDetailsController controller = loader.getController();
		Stage dialogStage = new Stage();
		Window window = null;
		Object source = event.getSource();

		controller.setTaskDetails(taskDetails);
		dialogStage.setTitle("Edit Task");
		dialogStage.initModality(Modality.APPLICATION_MODAL);

		window = getRootWindow(window, source);

		dialogStage.initOwner(window);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();

	}

	private Window getRootWindow(Window window, Object source) {
		if (source instanceof TableRow) {
			TableRow<TaskDetails> row = (TableRow<TaskDetails>) source;
			window = row.getScene().getWindow();
		} else if (source instanceof MenuItem) {
			window = ((MenuItem) source).getParentPopup().getOwnerNode().getScene().getWindow();
		}
		return window;
	}

}
