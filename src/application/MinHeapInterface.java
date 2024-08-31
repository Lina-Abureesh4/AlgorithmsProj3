package application;

public interface MinHeapInterface<T extends Comparable<T>> {

	public void add(T newEntry);

	public T getMin();

	public T removeMin();

	public boolean isEmpty();

	public int getSize();
}
