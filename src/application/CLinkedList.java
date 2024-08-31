package application;

public class CLinkedList<T extends Comparable<T>> {

	private Node<T> head;

	public Node<T> getHead() {
		return head;
	}

	public void addAtHead(T data) {
		Node<T> n = new Node<>(data);
		if (head == null) {
			head = n;
			n.setNext(head);
		} else {
			n.setNext(head);
			Node<T> curr = head;
			while (curr.getNext() != head) {
				curr = curr.getNext();
			}
			head = n;
			curr.setNext(head);
		}
	}

	public void traverse() {
		Node<T> curr = head;
		if (curr != null) {
			do {
				System.out.println(curr);
				curr = curr.getNext();
			} while (curr != head);
		}
	}

	public Node<T> search(T data) {
		Node<T> curr = head;
		if (curr != null)
			do {
				if (curr.getData().compareTo(data) == 0) {
					return curr;
				} else {
					curr = curr.getNext();
				}
			} while (curr != head);

		return null;
	}
}
