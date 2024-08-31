package application;

public class MinHeap<T extends Comparable<T>> implements MinHeapInterface<T> {
	private int size;
	T[] arr;

	public MinHeap(int capacity) {
		arr = (T[]) new Comparable[capacity + 1];
	}

	private void swim(int k) {
		if (k > size)
			return;

		while (k > 1 && greater(k / 2, k)) {
			swap(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= size) {
			int j = 2 * k;
			if (j < size && greater(j, j + 1))
				j++;
			if (!greater(k, j))
				break;
			swap(k, j);
			k = j;
		}
	}

	private void swap(int i, int j) {
		if (i <= size && j <= size) {
			T temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}

	private boolean greater(int i, int j) {
		return arr[i].compareTo(arr[j]) > 0;
	}

	@Override
	public void add(T newEntry) {
		arr[++size] = newEntry;
		swim(size);
	}

	@Override
	public T getMin() {
		if (!isEmpty())
			return arr[1];
		return null;
	}

	@Override
	public T removeMin() {
		if (isEmpty())
			return null;

		T min = arr[1];
		swap(1, size);
		size--;
		sink(1);
		return min;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void traverse() {
		for (int i = 1; i <= size; i++)
			System.out.println(arr[i]);
		System.out.println();
	}
}
