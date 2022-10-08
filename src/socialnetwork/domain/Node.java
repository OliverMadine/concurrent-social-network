package socialnetwork.domain;

public class Node<T> {

  private final int key;
  private T value;
  private Node<T> next;

  public Node(int key) {
    this.key = key;
  }

  public Node(T value, int key) {
    this.key = key;
    this.value = value;
  }

  public int getKey() {
    return key;
  }

  public T getValue() {
    return value;
  }

  public Node<T> getNext() {
    return next;
  }

  public void setNext(Node<T> next) {
    this.next = next;
  }
}
