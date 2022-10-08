package socialnetwork;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import socialnetwork.domain.Message;

public class User extends Thread {

  private static final AtomicInteger nextId = new AtomicInteger(0);

  protected final SocialNetwork socialNetwork;
  private final int id;
  private final String name;

  public User(String username, SocialNetwork socialNetwork) {
    this.name = username;
    this.id = User.nextId.getAndIncrement();
    this.socialNetwork = socialNetwork;
  }

  public int getUserId() {
    return id;
  }

  @Override
  public void run() {
    socialNetwork.postMessage(this, socialNetwork.getAllUsers(), "Concurrency :(");
    List<Message> board = socialNetwork.userBoard(this).getBoardSnapshot();
    if (board.size() != 0) {
      socialNetwork.deleteMessage(board.get(0));
    }
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", name='" + name + '\'' + '}';
  }

  @Override
  public int hashCode() {
    return id;
  }
}
