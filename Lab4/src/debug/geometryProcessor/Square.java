package debug.geometryProcessor;

public class Square implements Shape {

	double length;
	double area;
	String colour;
	String name;

	public Square(double l, String c, String n) {
		length = l;
		colour = c;
		name = n;
	}

	public String getShape() {
		return "Square";
	}

	public String getName() {
		return name;
	}

	public String getColour() {
		return colour;
	}

	public void setLength(double l) {
		length = l;
		area = getArea();
	}

	public void setColour(String c) {
		colour = c;
	}

	public void setName(String n) {
		name = n;
	}

	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return 0;
	}

}
