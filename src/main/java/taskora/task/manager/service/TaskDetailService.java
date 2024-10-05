package taskora.task.manager.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import taskora.task.manager.App;
import taskora.task.manager.constants.DateConstants;
import taskora.task.manager.entity.Task;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.repository.TaskRepository;

public class TaskDetailService {

  private static TaskDetailService taskRepo;
  private static AtomicInteger id = new AtomicInteger();;

  public static TaskDetailService getInstance() {
    if (taskRepo == null) {
      taskRepo = new TaskDetailService();
    }
    return taskRepo;
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

  private TaskRepository taskRepository = App.getApplicationContext().getBean(TaskRepository.class);

  private ObservableList<TaskDetails> taskDetails = FXCollections.observableArrayList();

  private TaskDetailService() {

  }

  public TaskDetails getById(String id) {
    Task task = null;
    if (id != null && !id.isEmpty()) {
      task = taskRepository.findById(Long.parseLong(id)).orElse(null);
      if (task != null) {
        return new TaskDetails(task);
      }
    }
    return null;
  }

  public ObservableList<TaskDetails> getTasks() {

    taskDetails.clear();
    List<Task> tasks = taskRepository.findAll();
    tasks.forEach(task -> {
      taskDetails.add(new TaskDetails(task));
    });

    return taskDetails;
  }

  public Task saveTask(TaskDetails newTaskDetail) {

    return taskRepository.save(new Task(newTaskDetail));

  }

  public void setTasks(ObservableList<TaskDetails> tasks) {
    this.taskDetails = tasks;
  }

  public void deleteTask(TaskDetails selectedTask) {

    Task task = new Task(selectedTask);
    taskRepository.delete(task);

  }
  
  public ObservableList<TaskDetails> getTaskDetailsByStartDate(LocalDate date){
    taskDetails.clear();
    List<Task> tasks = taskRepository.findByStartDate(date);
    tasks.forEach(task -> {
      taskDetails.add(new TaskDetails(task));
    });
   return taskDetails;
  }
}
