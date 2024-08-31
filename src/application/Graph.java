package application;

public class Graph {

	private int numberOfVertices;
	private Vertex[] vertices;

	public Graph(int numberOfVertices) {
		this.numberOfVertices = numberOfVertices;
		vertices = new Vertex[numberOfVertices];
	}

	public int getNumberOfVertices() {
		return numberOfVertices;
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public void setVertices(Vertex[] vertices) {
		this.vertices = vertices;
	}

	public void setVertex(int index, Capital capital) {
		Vertex vertex = new Vertex(capital, index);
		vertices[index] = vertex;

	}

	public Vertex findVertex(String capital) {
		for (int i = 0; i < numberOfVertices; i++)
			if (vertices[i].getCapital().getCapitalName().compareToIgnoreCase(capital) == 0)
				return vertices[i];
		return null;
	}

	public void addEdge(Vertex stratVer, Vertex endVer) {
		if (stratVer.compareTo(endVer) == 0)
			return;

		stratVer.addEdge(endVer);
	}
}
