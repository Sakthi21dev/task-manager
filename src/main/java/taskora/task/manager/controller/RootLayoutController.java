package taskora.task.manager.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import taskora.task.manager.constants.TaskStatus;
import taskora.task.manager.implementation.AbstractConvertCellFactory;
import taskora.task.manager.implementation.DatePickerTableCell;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.service.TaskDetailService;
import taskora.task.manager.service.ValidationService;
import taskora.task.manager.utils.DateUtils;
import taskora.task.manager.utils.FxmlUtils;

/**
 * Controls TaskOfTheDayLayout's Table and search bar.
 * <p>
 * This class manages the table view, search bar, filtering, and editing logic
 * for tasks.
 * </p>
 */
public class RootLayoutController {

  @FXML
  private BorderPane rootPane;

  @FXML
  private TableView<TaskDetails> taskTableView;

  @FXML
  private TableColumn<TaskDetails, String> id;

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

  private TaskDetailService taskDetailService = TaskDetailService.getInstance();
  private FilteredList<TaskDetails> filteredData;

  /**
   * Initializes the controller. This method is automatically called after the
   * FXML file has been loaded.
   */
  @FXML
  public void initialize() {
    setupTableColumns();
    setupContextMenu();
    setupFilter();
    setupEditCommitHandlers();

    // Bind the filtered data to the table
    taskTableView.setItems(filteredData);
  }

  /**
   * Sets up the cell value factories for the TableView columns.
   */
  private void setupTableColumns() {
    id.setCellValueFactory(cellData -> cellData.getValue().idProperty());
    taskId.setCellValueFactory(cellData -> cellData.getValue().taskId());
    
    taskName.setCellValueFactory(cellData -> cellData.getValue().taskName());
    taskName.setCellFactory(TextFieldTableCell.forTableColumn()); // Enable editing for name column
    
    status.setCellValueFactory(cellData -> cellData.getValue().status());
    status.setCellFactory(ChoiceBoxTableCell.forTableColumn(TaskStatus.values()));
    
    startDate.setCellValueFactory(cellData -> cellData.getValue().startDate());
    startDate.setCellFactory(col -> new DatePickerTableCell());
    
    
    endDate.setCellValueFactory(cellData -> cellData.getValue().endDate());
    endDate.setCellFactory(
        (AbstractConvertCellFactory<TaskDetails, LocalDate>) value -> DateUtils.format(value));
    
    spendHours.setCellValueFactory(cellData -> cellData.getValue().spendHours());

    filteredData = new FilteredList<>(taskDetailService.getTasks(), b -> true);
  }

  /**
   * Sets up the context menu for the task table.
   */
  private void setupContextMenu() {
    ContextMenu contextMenu = new ContextMenu();

    MenuItem editMenuItem = new MenuItem("Edit Task");
    editMenuItem.setOnAction(event -> handleEditTask());

    contextMenu.getItems().addAll(new MenuItem("Copy Task"), editMenuItem,
        new MenuItem("Delete Task"));

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

  /**
   * Handles the edit task action from the context menu.
   */
  private void handleEditTask() {
    if (!taskTableView.getSelectionModel().isEmpty()) {
      TaskDetails selectedTask = taskTableView.getSelectionModel().getSelectedItem();
      try {
        goAddTaskOnEditMode(selectedTask);
      } catch (IOException e) {
        e.printStackTrace();
        // Consider showing a user-friendly message here
      }
    }
  }

  /**
   * Sets up filtering for the task list based on search input.
   */
  private void setupFilter() {
    searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
      filteredData.setPredicate(task -> {
        if (newValue == null || newValue.isEmpty()) {
          return true; // Show all tasks if search box is empty
        }
        String lowerCaseFilter = newValue.toLowerCase();
        return TaskDetailService.predicate(task, lowerCaseFilter);
      });
    });
  }

  /**
   * Sets up the handlers for edit commits on task name and status.
   */
  private void setupEditCommitHandlers() {
    taskName.setOnEditCommit(event -> {
      TaskDetails taskDetail = event.getRowValue();
      String newTaskName = event.getNewValue();
      if (ValidationService.validateTaskName(newTaskName)) {
        taskDetail.setTaskName(newTaskName);
        taskDetailService.saveTask(taskDetail); // Save changes
      }
    });

    status.setOnEditCommit(event -> {
      TaskDetails taskDetail = event.getRowValue();
      TaskStatus newValue = event.getNewValue(); 
      if(TaskStatus.COMPLETED.equals(newValue) && ValidationService.validateSpendHours(this.spendHours.getText()) ) {
        LocalDate newEndDate = LocalDate.now();
        this.endDate.setText(DateUtils.format(newEndDate));
        taskDetail.setEndDate(newEndDate);
        taskDetail.setStatus(newValue);
        taskDetailService.saveTask(taskDetail); // Save changes
      }
    });
    
    spendHours.setOnEditCommit(event -> {
      TaskDetails taskDetail = event.getRowValue();
      String newSpendHours = event.getNewValue();
      if(ValidationService.validateSpendHoursDuration(newSpendHours)) {
        this.spendHours.setText(newSpendHours);
        taskDetail.setSpendHours(newSpendHours);
        taskDetailService.saveTask(taskDetail);
      }
    });
    
    startDate.setOnEditCommit(event -> {
      TaskDetails taskDetail = event.getRowValue();
      LocalDate newStartDate = event.getNewValue();
      System.out.println(newStartDate);
      if(ValidationService.validateStartDate(newStartDate)) {
        this.startDate.setText(DateUtils.format(newStartDate));
        taskDetail.setStartDate(newStartDate);
        taskDetailService.saveTask(taskDetail); 
      }
      
    });
    
 // Enable editing for the table and specific columns
    taskTableView.setEditable(true);
    taskName.setEditable(true);
    status.setEditable(true);
    startDate.setEditable(true);
    spendHours.setEditable(true);

    
  }

  /**
   * Opens the AddTaskDetails dialog in edit mode.
   *
   * @param taskDetails The task details to edit.
   * @throws IOException If an error occurs during dialog loading.
   */
  private void goAddTaskOnEditMode(TaskDetails taskDetails) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(FxmlUtils.getURL("AddTaskDetails"));

    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Edit Task");

    // Set the owner of the dialog
    dialogStage.initOwner(getRootWindow());

    // Load the dialog layout and controller
    GridPane taskDetailsLayout = loader.load();
    AddTaskDetailsController controller = loader.getController();
    controller.setTaskDetails(taskDetails); // Pass the task details to the controller

    Scene scene = new Scene(taskDetailsLayout);
    dialogStage.setScene(scene);
    dialogStage.showAndWait(); // Show the dialog and wait for it to close
  }

  /**
   * Retrieves the root window for the dialog stage.
   *
   * @return The window where the dialog will be displayed.
   */
  private Window getRootWindow() {
    return taskTableView.getScene().getWindow();
  }
}
