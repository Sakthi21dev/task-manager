package taskora.task.manager.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import taskora.task.manager.constants.TaskStatus;
import taskora.task.manager.constants.WarningMessage;
import taskora.task.manager.utils.DateUtils;

public class ValidationService {

  public static boolean validateTaskName(TextField taskName) {
    return validateTaskName(taskName.getText());
  }

  public static boolean validateTaskName(String taskName) {
    if (taskName.trim().isEmpty()) {
      showAlert(WarningMessage.EMPTY_TASK_NAME.toString());
      return false;
    }
    return true;
  }

  public static boolean validateSpendHours(ChoiceBox<TaskStatus> status, TextField spendHours) {
    return validateSpendHours(status.getValue(), spendHours.getText());
  }

  public static boolean validateSpendHours(TaskStatus status, String spendHours) {
    if (TaskStatus.COMPLETED.equals(status)) {
      String taskSpendHours = spendHours.trim();
      if (taskSpendHours.isEmpty()) {
        showAlert(WarningMessage.EMPTY_SPEND_HOURS.toString());
        return false;
      }

      if (!DateUtils.isValidDuration(taskSpendHours)) {
        showAlert(WarningMessage.INVALID_SPEND_HOURS_FORMAT.toString());
        return false;
      }
    }
    return true;
  }

  private static void showAlert(String message) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait(); // Show the alert and wait for it to be closed
  }

}
