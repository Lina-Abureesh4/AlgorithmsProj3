package application;

public class Capital implements Comparable<Capital> {

	private String capitalName;
	private double longitude;
	private double latitude;

	public Capital(String capitalName, double latitude, double longitude) {
		super();
		this.capitalName = capitalName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getCapitalName() {
		return capitalName;
	}

	public void setCapitalName(String capitalName) {
		this.capitalName = capitalName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Capital [capitalName=" + capitalName + ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}

	@Override
	public int compareTo(Capital o) {
		if (this.capitalName.equalsIgnoreCase(o.getCapitalName()))
			return 0;
		return -1;
	}

}
