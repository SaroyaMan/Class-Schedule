package classes;

public class Classroom {

	private int number;
	private int building;
	private int floor;
	
	public Classroom(int number) {
		setNumber(number);

	}
	public int getBuilding() {
		return building;
	}

	public int getFloor() {
		return floor;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
		this.building = Integer.parseInt(String.valueOf(String.valueOf(number).charAt(0)));
		this.floor = Integer.parseInt(String.valueOf(String.valueOf(number).charAt(1)));
	}
	@Override
	public String toString() {
		return "Classroom [building=" + building + ", floor=" + floor + ", number=" + number + "]";
	}
	
	
	
	
}
