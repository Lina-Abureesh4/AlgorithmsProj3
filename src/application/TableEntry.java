package application;

public class TableEntry implements Comparable<TableEntry> {

	private Vertex vertex;
	private double distance;
	private boolean known;
	private Vertex path;

	public TableEntry(Vertex vertex, double distance, boolean known, Vertex path) {
		super();
		this.vertex = vertex;
		this.distance = distance;
		this.known = known;
		this.path = path;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean isKnown() {
		return known;
	}

	public void setKnown(boolean known) {
		this.known = known;
	}

	public Vertex getPath() {
		return path;
	}

	public void setPath(Vertex path) {
		this.path = path;
	}

	@Override
	public int compareTo(TableEntry o) {
		if (this.getDistance() < o.getDistance())
			return -1;
		else if (this.getDistance() > o.getDistance())
			return 1;
		else
			return 0;
	}

}
