package crs.course;

import java.util.ArrayList;

import crs.Course;
import crs.client.filehandling.FileHandler;

public class CourseRegistry {
	
	public ArrayList<Course> courses = new ArrayList<Course>();
	
	private CourseRegistry(String registry_file) {
		FileHandler.addFile("registry", registry_file);
		courses = FileHandler.loadRegistry();
	}
	
	public Course searchCourse(int crn) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCrn() == crn) {
				return courses.get(i);
			}
		}
		
		return null;
	}

	public static CourseRegistry loadRegistryFromFile(String registry_file) {
		return new CourseRegistry(registry_file);
	}
	
}