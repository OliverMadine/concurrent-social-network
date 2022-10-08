package socialnetwork.domain;

import java.util.LinkedList;
import java.util.List;

public class CoarseBoard implements Board {

  private final Node<Message> head = new Node<>(Integer.MAX_VALUE);
  private final Node<Message> tail = new Node<>(Integer.MIN_VALUE);
  private int size = 0;

  public CoarseBoard() {
    head.setNext(tail);
  }

  @Override
  public synchronized boolean addMessage(Message message) {
    final Node<Message> pred = findPred(message.getMessageId());
    final Node<Message> curr = pred.getNext();
    if (message.getMessageId() == curr.getKey()) {
      return false;
    }
    Node<Message> node = new Node<>(message, message.getMessageId());
    node.setNext(curr);
    pred.setNext(node);
    size++;
    return true;
  }

  private Node<Message> findPred(int messageID) {
    Node<Message> pred, curr = head;
    do {
      pred = curr;
      curr = curr.getNext();
    } while (curr.getKey() > messageID);
    return pred;
  }

  @Override
  public synchronized boolean deleteMessage(Message message) {
    final Node<Message> pred = findPred(message.getMessageId());
    final Node<Message> curr = pred.getNext();
    if (message.getMessageId() == curr.getKey()) {
      pred.setNext(curr.getNext());
      size--;
      return true;
    }
    return false;
  }

  @Override
  public synchronized int size() {
    return size;
  }

  @Override
  public synchronized List<Message> getBoardSnapshot() {
    final LinkedList<Message> snapshot = new LinkedList<>();
    for (Node<Message> curr = head.getNext(); curr != tail; curr = curr.getNext()) {
      snapshot.add(curr.getValue());
    }
    return snapshot;
  }

  @Override
  public String toString() {
    return getBoardSnapshot().toString();
  }
}
