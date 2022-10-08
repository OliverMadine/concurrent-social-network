package socialnetwork;

import java.util.Optional;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.Node;
import socialnetwork.domain.Task;

public class CoarseBacklog implements Backlog {

  Node<Task> head, tail;
  int size = 0;

  @Override
  public synchronized boolean add(Task task) {
    Node<Task> node = new Node<>(task, task.getId());
    if (size == 0) {
      tail = head = node;
    } else {
      tail.setNext(node);
      tail = node;
    }
    size++;
    return true;
  }

  @Override
  public synchronized Optional<Task> getNextTaskToProcess() {
    if (size == 0) {
      return Optional.empty();
    }
    final Task task = head.getValue();
    head = head.getNext();
    if (--size == 0) {
      tail = null;
    }
    return Optional.of(task);
  }

  @Override
  public synchronized int numberOfTasksInTheBacklog() {
    return size;
  }
}
