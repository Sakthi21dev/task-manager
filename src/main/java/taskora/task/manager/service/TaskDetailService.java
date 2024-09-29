package taskora.task.manager.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import taskora.task.manager.App;
import taskora.task.manager.constants.DateConstants;
import taskora.task.manager.model.Task;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.repository.TaskRepository;

public class TaskDetailService {

  private static TaskDetailService taskRepo;
  private TaskRepository taskRepository = App.getApplicationContext()
      .getBean(TaskRepository.class);;

  private static AtomicInteger id = new AtomicInteger();
  private ObservableList<TaskDetails> taskDetails = FXCollections.observableArrayList();

  private TaskDetailService() {
    
  }

  public static TaskDetailService getInstance() {
    if (taskRepo == null) {
      taskRepo = new TaskDetailService();
    }
    return taskRepo;
  }

  public ObservableList<TaskDetails> getTasks() {

    taskDetails.clear();
    List<Task> tasks = taskRepository.findAll();
    tasks.forEach(task -> {
      taskDetails.add(new TaskDetails(task));
    });

    return taskDetails;
  }

  public TaskDetails getById(String id) {
    return taskDetails.stream().filter(task -> task.getTaskId().equals(id)).findFirst()
        .orElse(null);
  }

  public void setTasks(ObservableList<TaskDetails> tasks) {
    this.taskDetails = tasks;
  }

  public static String getNewId() {
    return String.valueOf(id.getAndIncrement());
  }

  public static boolean predicate(TaskDetails task, String matchText) {
    return task.getTaskId().toLowerCase().contains(matchText)
        || task.getTaskName().toLowerCase().contains(matchText)
        || task.getStatus().toString().toLowerCase().contains(matchText)
        || task.getSpendHours().contains(matchText)
        || task.getStartDate()
            .format(DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT.toString()))
            .toLowerCase().contains(matchText);
  }

  public Task saveTask(TaskDetails newTaskDetail) {

    return taskRepository.save(new Task(newTaskDetail));

  }
}
