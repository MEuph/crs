package crs.course;

import java.util.ArrayList;

import crs.Course;
import crs.client.filehandling.FileHandler;

public class CourseRegistry {
	
	public ArrayList<Course> courses = new ArrayList<Course>();
	
	private CourseRegistry(String registry_file) {
		// TODO Unimpplemented constructor CourseRegistry(registry_file: String)
		FileHandler.addFile("registry", registry_file);
		courses = FileHandler.loadRegistry();

		for (Course c : courses) {
			System.out.println();
			System.out.println(c.toString());
		}
	}
	
	public Course searchCourse(int crn) {
		// TODO: Unimplemented method searchCourse(crn: int): Course
		
		return null;
	}

	public static CourseRegistry loadRegistryFromFile(String registry_file) {
		return new CourseRegistry(registry_file);
	}
	
}