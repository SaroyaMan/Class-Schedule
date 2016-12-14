package classes;

public class Classroom {

	private int number;
	private int building;
	private int floor;
	
	public Classroom(int number, int building, int floor) {
		this.building = building;
		this.floor = floor;
		this.number = number;
	}
	public int getBuilding() {
		return building;
	}
	public void setBuilding(int building) {
		this.building = building;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Classroom [building=" + building + ", floor=" + floor + ", number=" + number + "]";
	}
	
	
	
	
}
