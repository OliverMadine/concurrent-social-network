package socialnetwork.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class FineOrderedSet<T> {

  private final LockableNode<T> head = new LockableNode<>(Integer.MAX_VALUE);
  private final LockableNode<T> tail = new LockableNode<>(Integer.MIN_VALUE);
  private final AtomicInteger size = new AtomicInteger(0);

  public FineOrderedSet() {
    head.setNext(tail);
  }

  public boolean add(T value, int key) {
    LockableNode<T> pred = null, curr = null;
    try {
      pred = findPred(key);
      curr = pred.getNext();
      if (key == curr.getKey()) {
        return false;
      }
      LockableNode<T> node = new LockableNode<>(value, key);
      node.setNext(curr);
      pred.setNext(node);
      size.incrementAndGet();
      return true;
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  private LockableNode<T> findPred(int messageID) {
    LockableNode<T> pred = head, curr;
    pred.lock();
    curr = pred.getNext();
    curr.lock();
    while (curr.getKey() > messageID) {
      pred.unlock();
      pred = curr;
      curr = curr.getNext();
      curr.lock();
    }
    return pred;
  }

  public boolean delete(int key) {
    LockableNode<T> pred = null, curr = null;
    try {
      pred = findPred(key);
      curr = pred.getNext();
      if (key == curr.getKey()) {
        pred.setNext(curr.getNext());
        size.decrementAndGet();
        return true;
      }
      return false;
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  public int size() {
    return size.get();
  }

  // O(n) :(
  public Optional<T> poll() {
    LockableNode<T> pred = head, curr = null;
    try {
      pred.lock();
      curr = pred.getNext();
      curr.lock();
      if (curr == tail) {
        return Optional.empty();
      }
      while (curr.getNext() != tail) {
        pred.unlock();
        pred = curr;
        curr = curr.getNext();
        curr.lock();
      }
      pred.setNext(tail);
      size.decrementAndGet();
      return Optional.of(curr.getValue());
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  public List<T> snapshot() {
    final LinkedList<T> snapshot = new LinkedList<>();
    LockableNode<T> pred, curr = null;
    try {
      head.lock();
      curr = head.getNext();
      curr.lock();
      head.unlock();
      while (curr != tail) {
        snapshot.add(curr.getValue());
        pred = curr;
        curr = curr.getNext();
        curr.lock();
        pred.unlock();
      }
      return snapshot;
    } finally {
      curr.unlock();
    }
  }

  @Override
  public String toString() {
    return snapshot().toString();
  }
}