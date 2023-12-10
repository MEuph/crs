package crs.course;

import crs.Course;

public class Lecture extends Course {
	
	private Lab linked_lab = new Lab();
	
	@Override
	public String stringRepresentation() {
		return this.department + " " + this.course_number + " | " + this.name + "\n\tInstructor: " + this.instructor + "\n\tTAs: " + this.TAs.toString() + "\n\tHours: " + this.credit_hours + "\n\tSeats Remaining: " + this.number_of_seats + "\n\tLinked Lab: " + this.linked_lab.toString() + "\n\tDescription: " + this.description;
	}
	
	public Lab getLinkedLab() {
		return linked_lab;
	}
	
	public void setLinkedLab(Lab linked_lab) {
		this.linked_lab = linked_lab;
	}
}