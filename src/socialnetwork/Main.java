package socialnetwork;

import java.util.Arrays;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.FineBoard;
import socialnetwork.domain.Worker;

public class Main {

  public static void main(String[] args) {
    Backlog backlog = new FineBacklog();
    SocialNetwork socialNetwork = new SocialNetwork(backlog);

    Worker[] workers = new Worker[50];
    Arrays.setAll(workers, i -> new Worker(backlog));
    Arrays.stream(workers).forEach(Thread::start);

    User[] userThreads = new User[50];
    for (int i = 0; i < userThreads.length; i++) {
      User user = new User("user" + i, socialNetwork);
      userThreads[i] = user;
      socialNetwork.register(user, new FineBoard());
      user.start();
    }

    for (User user : userThreads) {
      try {
        user.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    while (backlog.numberOfTasksInTheBacklog() != 0) {
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    Arrays.stream(workers).forEach(Worker::interrupt);
    for (Worker worker : workers) {
      try {
        worker.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
