package crs.client.filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import crs.Course;
import crs.course.Lab;
import crs.course.Lecture;

public class FileHandler {

	private static HashMap<String, ArrayList<String>> files = new HashMap<>();

	public static void addFile(String key, String file_path) {
		try {
			System.out.println("Loading file: " + file_path);
			File f = new File(file_path);
			Scanner sc = new Scanner(f);
			ArrayList<String> data = new ArrayList<>();

			while (sc.hasNextLine()) {
				String s = sc.nextLine();
				data.add(s);
			}

			FileHandler.files.put(key, data);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void removeFile(String key) {
		FileHandler.files.remove(key);
	}

	public static ArrayList<String> getFile(String key) {
		return FileHandler.files.get(key);
	}

	public static HashMap<String, List<Course>> getPrereqs() {
		HashMap<String, List<Course>> prereqs = new HashMap<>();
		ArrayList<String> prereq_data = FileHandler.getFile("prereqs");

		int l = 1;

		for (String line : prereq_data) {
			String[] tokens = line.split("\\s+");
			List<Course> courses = new ArrayList<>();

			if (tokens.length < 3) {
				System.err.println("Malformed prereq data at line " + l);
			} else if (tokens[2].equals("|")) {
				for (int i = 3; i < tokens.length; i += 3) {
					Lecture lect = new Lecture();

					lect.setDepartment(tokens[i]);
					lect.setCourseNumber(Integer.parseInt(tokens[i + 1]));

					courses.add(lect);

					if (tokens[i + 2].equals(";")) {
						break;
					}

					if (!tokens[i + 2].equals(",")) {
						System.err.println("Malformed prereq data at line " + l);
					} else {
						continue;
					}
				}
			} else if (!tokens[2].equals(";")) {
				System.err.println("Malformed prereq data at line " + l);
			}

			prereqs.put(tokens[0] + " " + tokens[1], courses);

			l++;
		}

		return prereqs;
	}

	public static ArrayList<Course> loadRegistry() {
		ArrayList<Course> registry = new ArrayList<>();
		ArrayList<String> registry_data = FileHandler.getFile("registry");

		int l = 1;

		for (String line : registry_data) {
			String[] tokens = line.split("\\s+");
			
			Course course = null;

			if (tokens[2].equals("|")) {

				String dept = tokens[0];
				int course_number = Integer.parseInt(tokens[1]);

				int section = Integer.parseInt(tokens[3]);

				if (section >= 500) {
					// Lab
					course = new Lab();

					int i = 5;

					int crn = Integer.parseInt(tokens[i]);

					i += 2;
					int linked_lecture = Integer.parseInt(tokens[i]);

					i += 2;
					String instructor_name = "";
					for (; !tokens[i].equals("|"); i++) {
						instructor_name += tokens[i] + " ";
					}

					i++;
					ArrayList<String> TAs = new ArrayList<>();
					if (!tokens[i].equals("x")) {
						String TA_names = "";
						for (; !tokens[i].equals("|"); i++) {
							TA_names += tokens[i] + " ";
						}

						String[] TA_names_list = TA_names.split(",");
						for (String s : TA_names_list) {
							TAs.add(s);
						}
					}

					i++;
					int seats = Integer.parseInt(tokens[i]);
					
					course.setCourseNumber(course_number);
					course.setCreditHours(Integer.parseInt(Integer.toString(course_number).substring(1, 2)));
					course.setCrn(crn);
					course.setDepartment(dept);
					course.setNumberOfSeats(seats);
					course.setTAs(TAs);
					course.setInstructor(instructor_name);
					course.setLinked_section(linked_lecture);
				} else {
					// Lecture
					course = new Lecture();

					int i = 5;
					String name = "";
					for (; !tokens[i].equals("|"); i++) {
						name += tokens[i] + " ";
					}
					System.out.println("Detected course name: " + name);

					String desc = "";
					i++;
					for (; !tokens[i].equals("|"); i++) {
						desc += tokens[i] + " ";
					}
					
					System.out.println("Detected course desc: " + desc);

					i++;
					int crn = Integer.parseInt(tokens[i]);
					
					System.out.println("Detected crn: " + crn);

					i += 2;
					int linked_lab = -1;
					if (!tokens[i].equals("x")) {
						linked_lab = Integer.parseInt(tokens[i]);
					}
					
					System.out.println("Detected lab: " + linked_lab);

					i += 2;
					String instructor_name = "";
					for (; !tokens[i].equals("|"); i++) {
						instructor_name += tokens[i] + " ";
					}
					
					System.out.println("Detected instructor: " + instructor_name);

					i++;
					ArrayList<String> TAs = new ArrayList<>();
					if (!tokens[i].equals("x")) {
						String TA_names = "";
						for (; !tokens[i].equals("|"); i++) {
							TA_names += tokens[i] + " ";
						}

						String[] TA_names_list = TA_names.split(",");
						for (String s : TA_names_list) {
							TAs.add(s);
						}
					}

					i+=2;
					int seats = Integer.parseInt(tokens[i]);
					
					course.setCourseNumber(course_number);
					course.setCreditHours(Integer.parseInt(Integer.toString(course_number).substring(1, 2)));
					course.setCrn(crn);
					course.setDepartment(dept);
					course.setDescription(desc);
					course.setName(name);
					course.setNumberOfSeats(seats);
					course.setTAs(TAs);
					course.setInstructor(instructor_name);
					course.setLinked_section(linked_lab);
				}

			} else {
				System.err.println("Malformed prereq data at line " + l);
			}
			
			if (course == null) {
				System.err.println("Error trying to add course at line " + l);
			} else {
				registry.add(course);
			}

			l++;
		}
		
		return registry;
	}
}