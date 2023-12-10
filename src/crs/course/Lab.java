package crs.course;

import crs.Course;

public class Lab extends Course {
	
	private Lecture linked_lecture;
	
	public Lab() {
		
	}
	
	@Override
	public String stringRepresentation() {
		return "\n\t\t(Lab)" + this.department + " " + this.course_number + " | " + this.name + "\n\t\tInstructor: " + this.instructor + "\n\t\tTAs: " + this.TAs.toString() + "\n\t\tHours: " + this.credit_hours + "\n\t\tSeats Remaining: " + this.number_of_seats + "\n\t\tDescription: " + this.description;
	}

	public Lecture getLinkedLecture() {
		return linked_lecture;
	}
	
	public void setLinkedLecture(Lecture linked_lecture) {
		this.linked_lecture = linked_lecture;
	}

}