package socialnetwork.domain;

import java.util.List;

public class FineBoard implements Board {

  private final FineOrderedSet<Message> list = new FineOrderedSet<>();

  @Override
  public boolean addMessage(Message message) {
    return list.add(message, message.getMessageId());
  }

  @Override
  public boolean deleteMessage(Message message) {
    return list.delete(message.getMessageId());
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public List<Message> getBoardSnapshot() {
    return list.snapshot();
  }
}
