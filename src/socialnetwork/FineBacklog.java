package socialnetwork;

import java.util.Optional;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.FineOrderedSet;
import socialnetwork.domain.Task;

public class FineBacklog implements Backlog {

  private final FineOrderedSet<Task> list = new FineOrderedSet<>();

  @Override
  public boolean add(Task task) {
    return list.add(task, task.getId());
  }

  @Override
  public Optional<Task> getNextTaskToProcess() {
    return list.poll();
  }

  @Override
  public int numberOfTasksInTheBacklog() {
    return list.size();
  }
}
