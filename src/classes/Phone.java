package classes;

public class Phone {

	private int idLecturer;
	private String number;
	
	public Phone(String number, int idLecturer) {
		this.idLecturer = idLecturer;
		this.number = number;
	}
	public int getIdLecturer() {
		return idLecturer;
	}
	public void setIdLecturer(int id) {
		this.idLecturer = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Phone [idLecturer=" + idLecturer + ", number=" + number + "]";
	}
}