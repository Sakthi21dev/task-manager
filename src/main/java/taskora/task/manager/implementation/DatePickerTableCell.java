package taskora.task.manager.implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.utils.DateUtils;

public class DatePickerTableCell extends TableCell<TaskDetails, LocalDate> {
    private final DatePicker datePicker = new DatePicker();
    private final DateTimeFormatter formatter = DateUtils.getDefaultFormatter();

    public DatePickerTableCell() {
        // Commit edit when a date is selected
        datePicker.setOnAction(e -> commitEdit(datePicker.getValue()));
        datePicker.setConverter(DateUtils.getConverter());
        // Handle keyboard input for committing or canceling edit
        datePicker.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit(); // Cancel on ESC
            }
        });
    }

    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (!isEditing()) {
                // Display formatted date as text when not editing
                setText(item.format(formatter)); 
                setGraphic(null); // No graphic when not editing
            } else {
                datePicker.setValue(item);
                setGraphic(datePicker); // Show DatePicker when editing
                datePicker.requestFocus(); // Focus the DatePicker
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (!isEmpty()) {
            datePicker.setValue(getItem());
            setGraphic(datePicker);
            setText(null); // Clear text when entering edit mode
            datePicker.requestFocus(); // Focus the DatePicker
        }
    }

    @Override
    public void commitEdit(LocalDate newValue) {
        if (getTableRow() != null) {
            getTableRow().getItem().setStartDate(newValue); // Update the model
        }
        super.commitEdit(newValue);
        setGraphic(null); // Reset graphic after committing
        setText(newValue.format(formatter)); // Update displayed text
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null); // Reset graphic when editing is canceled
        setText(getItem() != null ? getItem().format(formatter) : ""); // Reset displayed text
    }
}
