package taskora.task.manager.service;

import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import taskora.task.manager.constants.TaskStatus;
import taskora.task.manager.constants.WarningMessage;
import taskora.task.manager.utils.DateUtils;

public class ValidationService {

  /**
   * Displays a warning alert with the provided message.
   *
   * @param message The message to be displayed in the alert.
   */
  private static void showAlert(String message) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait(); // Show the alert and wait for it to be closed
  }

  /**
   * Validates the spend hours based on the task status.
   *
   * @param status     The task status choice box.
   * @param spendHours The text field for spend hours.
   * @return True if valid, otherwise false.
   */
  public static boolean validateSpendHours(ChoiceBox<TaskStatus> status, TextField spendHours) {
    return validateTaskStatus(status.getValue(), spendHours.getText());
  }

  /**
   * Validates the task status and the corresponding spend hours.
   *
   * @param status     The task status.
   * @param spendHours The spend hours as a string.
   * @return True if valid, otherwise false.
   */
  public static boolean validateTaskStatus(TaskStatus status, String spendHours) {
    return !TaskStatus.COMPLETED.equals(status) || validateSpendHours(spendHours);
  }

  /**
   * Validates the spend hours input.
   *
   * @param spendHours The spend hours as a string.
   * @return True if valid, otherwise false.
   */
  public static boolean validateSpendHours(String spendHours) {
    if (spendHours == null) {
      spendHours = "";
    }
    return validateSpendHoursIsNotEmpty(spendHours) && validateSpendHoursDuration(spendHours);
  }

  /**
   * Validates that the spend hours follow the correct duration format.
   *
   * @param spendHours The spend hours as a string.
   * @return True if valid format, otherwise false.
   */
  public static boolean validateSpendHoursDuration(String spendHours) {
    if (!DateUtils.isValidDuration(spendHours)) {
      showAlert(WarningMessage.INVALID_SPEND_HOURS_FORMAT.toString());
      return false;
    }
    return true;
  }

  /**
   * Checks if spend hours field is not empty.
   *
   * @param spendHours The spend hours as a string.
   * @return True if not empty, otherwise false.
   */
  public static boolean validateSpendHoursIsNotEmpty(String spendHours) {
    if (spendHours.trim().isEmpty()) {
      showAlert(WarningMessage.EMPTY_SPEND_HOURS.toString());
      return false;
    }
    return true;
  }

  /**
   * Validates that the task name is not empty.
   *
   * @param taskName The task name as a string.
   * @return True if valid, otherwise false.
   */
  public static boolean validateTaskName(String taskName) {
    if (taskName.trim().isEmpty()) {
      showAlert(WarningMessage.EMPTY_TASK_NAME.toString());
      return false;
    }
    return true;
  }

  /**
   * Validates the task name from a TextField.
   *
   * @param taskName The TextField containing the task name.
   * @return True if valid, otherwise false.
   */
  public static boolean validateTaskName(TextField taskName) {
    return validateTaskName(taskName.getText());
  }

  /**
   * Validates the start date.
   *
   * @param startDate The start date to validate.
   * @return True if valid, otherwise false.
   */
  public static boolean validateStartDate(LocalDate startDate) {
    // Placeholder for start date validation logic
    return true; // Implement your validation logic here
  }
}
