package socialnetwork.domain;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockableNode<T> {

  private final Lock lock = new ReentrantLock();
  private int key;
  private T value;
  private LockableNode<T> next;

  public LockableNode(int key) {
    this.key = key;
  }

  public LockableNode() {}

  public LockableNode(T value, int key) {
    this.key = key;
    this.value = value;
  }

  public int getKey() {
    return key;
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  public T getValue() {
    return value;
  }

  public LockableNode<T> getNext() {
    return next;
  }

  public void setNext(LockableNode<T> next) {
    this.next = next;
  }
}
