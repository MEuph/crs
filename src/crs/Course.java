package crs;

import java.util.ArrayList;

public abstract class Course {
	
	protected int number_of_seats;

	protected String department;
	protected String name;
	protected String description;
	protected String instructor;
	
	public int crn;
	
	public int course_number;
	public int credit_hours;
	
	public int linked_crn;
	
	public ArrayList<String> TAs = new ArrayList<>();
	
	public abstract String stringRepresentation();
	
	@Override
	public String toString() {
		return stringRepresentation();
	}

	public int getNumberOfSeats() {
		return number_of_seats;
	}

	public void setNumberOfSeats(int number_of_seats) {
		this.number_of_seats = number_of_seats;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCourseNumber() {
		return course_number;
	}

	public void setCourseNumber(int course_number) {
		this.course_number = course_number;
	}

	public int getCreditHours() {
		return credit_hours;
	}

	public void setCreditHours(int credit_hours) {
		this.credit_hours = credit_hours;
	}

	public ArrayList<String> getTAs() {
		return TAs;
	}

	public void setTAs(ArrayList<String> tAs) {
		TAs = tAs;
	}
	
	public int getCrn() {
		return crn;
	}
	
	public void setCrn(int crn) {
		this.crn = crn;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public String getInstructor() {
		return instructor;
	}
	
	public void setLinked_section(int linked_section) {
		this.linked_crn = linked_section;
	}
	
	public int getLinked_section() {
		return linked_crn;
	}
}