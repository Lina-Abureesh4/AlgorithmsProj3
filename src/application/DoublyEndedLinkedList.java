package application;

public class DoublyEndedLinkedList<T extends Comparable<T>> {

	private Node<T> head;
	private Node<T> tail;

	public void addAtStart(T data) { // T(n) = c
		Node<T> newNode = new Node<T>(data);
		if (head == null) { // empty
			head = newNode;
			tail = newNode;
		} else {
			newNode.setNext(head);
			head = newNode;
		}
	}

	public void addAtEnd(T data) { // T(n) = c
		Node<T> newNode = new Node<T>(data);
		if (head == null) { // empty
			head = newNode;
			tail = newNode;
		} else {
			tail.setNext(newNode);
			tail = newNode;
		}
	}

	public boolean isEmpty() {
		return this.head == null;
	}

	public int length() { // T(n) = O(n)
		int length = 0;
		Node<T> curr = head;
		while (curr != null) {
			length++;
			curr = curr.getNext();
		}
		return length;
	}

	public Node<T> search(T data) {
		Node<T> curr = head;
		while (curr != null) {
			if (curr.getData().compareTo(data) == 0)
				return curr;

			else
				curr = curr.getNext();
		}
		return null;
	}

	public Node<T> getHead() {
		return this.head;
	}

	public Node<T> getTail() {
		return this.tail;
	}
}
