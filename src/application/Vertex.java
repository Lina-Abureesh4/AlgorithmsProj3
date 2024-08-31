package application;

public class Vertex implements Comparable<Vertex> {

	private Capital capital;
	private DoublyEndedLinkedList<Edge> edges;
	private int number; 

	public Vertex(Capital capital, int number) {
		super();
		this.capital = capital;
		edges = new DoublyEndedLinkedList<>();
		this.number = number; 
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Vertex(Capital capital, DoublyEndedLinkedList<Edge> edges) {
		super();
		this.capital = capital;
		this.edges = edges;
	}

	public Capital getCapital() {
		return capital;
	}

	public void setCapital(Capital capital) {
		this.capital = capital;
	}

	public DoublyEndedLinkedList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(DoublyEndedLinkedList<Edge> edges) {
		this.edges = edges;
	}

	public void addEdge(Vertex endVertex) {
		this.edges.addAtEnd(new Edge(this, endVertex));
	}

	public Edge getEdgeWith(Vertex endVertex) {
		Node<Edge> node = this.edges.search(new Edge(this, endVertex));
		if (node != null)
			return node.getData();

		return null;
	}

	@Override
	public int compareTo(Vertex o) {
		if (this.capital.compareTo(o.getCapital()) == 0)
			return 0;

		return -1;
	}

	@Override
	public String toString() {
		return capital.getCapitalName() + "";
	}

	
}
