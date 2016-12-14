package classes;

import java.sql.Date;

public class Lecturer {
	
	private int id;
	private String f_name;
	private String l_name;
	private String address;
	private Date birthdate;
	private int age;
	
	
	public Lecturer(int id, String f_name, String l_name, String address, Date birthdate, int age) {
		this.f_name = f_name;
		this.l_name = l_name;
		this.id = id;
		this.birthdate = birthdate;
		this.age = age;
		this.address = address;
	}
	public String getFirstName() {
		return f_name;
	}
	public void setFirstName(String f_name) {
		this.f_name = f_name;
	}
	public String getLastName() {
		return l_name;
	}
	public void setLastName(String l_name) {
		this.l_name = l_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Lecturer [f_name=" + f_name + ", l_name=" + l_name + ", id=" + id + ", birthdate=" + birthdate
				+ ", age=" + age + ", address=" + address + "]";
	}
	
	
}
