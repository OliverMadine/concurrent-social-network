package socialnetwork.domain;

import java.util.Optional;

public class Worker extends Thread {

  private final Backlog backlog;
  private boolean interrupted = false;

  public Worker(Backlog backlog) {
    this.backlog = backlog;
  }

  @Override
  public void run() {
    while (!interrupted) {
      Optional<Task> next = backlog.getNextTaskToProcess();
      if (next.isPresent()) {
        process(next.get());
      } else {
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void interrupt() {
    this.interrupted = true;
  }

  public void process(Task nextTask) {
    switch (nextTask.command) {
      case POST -> nextTask.board.addMessage(nextTask.message);
      case DELETE -> {
        if (!nextTask.board.deleteMessage(nextTask.message)) {
          backlog.add(nextTask);
        }
      }
    }
  }
}
