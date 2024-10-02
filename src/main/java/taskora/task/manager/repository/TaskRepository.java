package taskora.task.manager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taskora.task.manager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findByStartDate(LocalDate date);

}
