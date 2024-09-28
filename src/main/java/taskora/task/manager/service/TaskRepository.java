package taskora.task.manager.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import taskora.task.manager.constants.DateConstants;
import taskora.task.manager.constants.TaskStatus;
import taskora.task.manager.model.TaskDetails;

public class TaskRepository {

	private static TaskRepository taskRepo;

	private static AtomicInteger id = new AtomicInteger();

	private TaskRepository() {
		TaskDetails newTask = new TaskDetails();
		newTask.setTaskId(getNewId());
		newTask.setTaskName("Example");
		newTask.setStatus(TaskStatus.COMPLETED);
		newTask.setAssignedDate(LocalDate.now());
		newTask.setStartDate(LocalDate.now());
		newTask.setEndDate(LocalDate.now());
		newTask.setEstimatedHours("8");
		newTask.setSpendHours("8");
		tasks.add(newTask);
	}

	public static TaskRepository getInstance() {
		if (taskRepo == null) {
			taskRepo = new TaskRepository();
		}
		return taskRepo;
	}

	private ObservableList<TaskDetails> tasks = FXCollections.observableArrayList();

	public ObservableList<TaskDetails> getTasks() {
		return tasks;
	}

	public TaskDetails getById(String id) {
		return tasks.stream().filter(task -> task.getTaskId().equals(id)).findFirst().orElse(null);
	}

	public void setTasks(ObservableList<TaskDetails> tasks) {
		this.tasks = tasks;
	}

	public static String getNewId() {
		return String.valueOf(id.getAndIncrement());
	}
	
	public static boolean predicate(TaskDetails task, String matchText) {
		return task.getTaskId().toLowerCase().contains(matchText) || task.getTaskName().toLowerCase().contains(matchText) 
				|| task.getStatus().toString().toLowerCase().contains(matchText) || task.getSpendHours().contains(matchText)
				|| task.getStartDate().format( DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT.toString())).toLowerCase().contains(matchText); 
	}
}
