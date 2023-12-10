package crs.client;

import java.util.HashMap;
import java.util.List;

import crs.CRSArgumentsHandler;
import crs.Client;
import crs.Course;
import crs.Main;
import crs.SecurityLevel;
import crs.client.filehandling.FileHandler;
import crs.course.Student;

public class StudentClient extends SecureClient {

	private Student current_user;

	private ArgumentsHandler arguments_handler;

	private StudentClient(String user_key, SecurityLevel base_level) {
		super(user_key, base_level);
	}

	@Override
	protected SecurityLevel calculateElevationChange(SecurityLevel base_level, String user_key) {
		if (base_level != SecurityLevel.LEVEL_NONE) {
			return SecurityLevel.LEVEL_STUDENT;
		} else {
			return base_level;
		}
	}

	private HashMap<String, List<Course>> loadPreReq() {
		return FileHandler.getPrereqs();
	}

	public static StudentClient loadClient(String args[]) {
		String user_key = CRSArgumentsHandler.getParameter("k", "-key", 0);
		SecurityLevel base_level = SecurityLevel.LEVEL_NONE;

		StudentClient client = new StudentClient(user_key, base_level);

		client.arguments_handler = new ArgumentsHandler(client.security_level);

		client.pre_req = client.loadPreReq();

		return client;
	}

	@Override
	public void addCourse() {
		System.out.print("Enter CRN: ");
		int crn = Main.user_input.nextInt();
		Course c = Main.course_registry.searchCourse(crn);
		if (c != null) {
			schedule.add(c);
			c.setNumberOfSeats(c.getNumberOfSeats() - 1);
		} else {
			System.out.println("No course found matching CRN " + crn);
		}
	}

	@Override
	public void dropCourse() {
		System.out.print("Enter CRN: ");
		int crn = Main.user_input.nextInt();
		Course c = Main.course_registry.searchCourse(crn);
		if (c != null) {
			if (schedule.contains(c)) {
				schedule.remove(c);
				c.setNumberOfSeats(c.getNumberOfSeats() + 1);
			}
		} else {
			System.out.println("No course found matching CRN " + crn);
		}
	}

	@Override
	public void searchCourse() {
		System.out.print("Enter CRN: ");
		int crn = Main.user_input.nextInt();
		Course c = Main.course_registry.searchCourse(crn);
		if (c != null)
			System.out.println("Found course\n" + c.stringRepresentation());
		else
			System.out.println("No course found matching CRN " + crn);
	}
	
	@Override
	public void printSchedule() {
		for (Course c : schedule) {
			System.out.println(c.toString());
		}
	}

	@Override
	public void run() {
		getOptions();

		int option = getInput();

		while (option != Client.OPTION_EXIT) {
			if (option > max_option) {
				System.err.println("Insufficient privileges to perform action!");
			} else {
				switch (option) {
				case Client.OPTION_HELP:
					getOptions();
					break;
				case Client.OPTION_ADD_COURSE:
					System.out.println("Adding course...");
					addCourse();
					break;
				case Client.OPTION_DROP_COURSE:
					System.out.println("Dropping course...");
					dropCourse();
					break;
				case Client.OPTION_SEARCH_COURSE:
					System.out.println("Searching for a course...");
					searchCourse();
					break;
				case Client.OPTION_PRINT_SCHEDULE:
					System.out.println("Print schedule...");
					printSchedule();
					break;
				case -1:
					System.err.println("Error getting option in +AdminClient.run(): void");
					Main.user_input.close();
					System.exit(Client.EXIT_FATAL);
					break;
				default:
					System.err.println("Uknown option (" + option + ") in +AdminClient.run(): void");
					break;
				}
			}

			option = getInput();
		}

		exitWithCode(Client.EXIT_SUCCESS);
	}

	@Override
	public void exitWithCode(int error_code) {
		if (error_code == Client.EXIT_FATAL_UNDEFINED) {
			System.err.println("Some method was implemented but never defined properly");
		}
		this.return_code = error_code;
	}

	public Student getCurrentUser() {
		return current_user;
	}

	public ArgumentsHandler getArgumentsHandler() {
		return arguments_handler;
	}

	public void setArgumentsHandler(ArgumentsHandler arguments_handler) {
		this.arguments_handler = arguments_handler;
	}
}