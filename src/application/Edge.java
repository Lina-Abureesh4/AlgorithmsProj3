package application;

public class Edge implements Comparable<Edge> {

	private Vertex start;
	private Vertex end;
	private Double weight;

	public Edge(Vertex start, Vertex end) {
		this.start = start;
		this.end = end;
		calculate_weight();
	}

	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex start) {
		this.start = start;
	}

	public Vertex getEnd() {
		return end;
	}

	public void setEnd(Vertex end) {
		this.end = end;
	}

	public Double getWeight() {
		return weight;
	}

	public void calculate_weight() {
		double longitude1 = start.getCapital().getLongitude();
		double longitude2 = end.getCapital().getLongitude();
		double latitude1 = start.getCapital().getLatitude();
		double latitude2 = end.getCapital().getLatitude();

		double lat1_radian = Math.toRadians(latitude1);
		double lat2_radian = Math.toRadians(latitude2);
		double long1_radian = Math.toRadians(longitude1);
		double long2_radian = Math.toRadians(longitude2);

		double earthRadius = 6371;

		double distance = earthRadius * Math.acos((Math.sin(lat1_radian) * Math.sin(lat2_radian))
				+ Math.cos(lat1_radian) * Math.cos(lat2_radian) * Math.cos(long1_radian - long2_radian));
		
		this.weight = distance;
	}

	@Override
	public int compareTo(Edge o) {
		if (this.getEnd().compareTo(o.getEnd()) == 0)
			return 0;
		return -1;
	}
}
