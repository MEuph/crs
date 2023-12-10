package crs.client;

import java.util.HashMap;
import java.util.List;

import crs.CRSArgumentsHandler;
import crs.Client;
import crs.Course;
import crs.Main;
import crs.SecurityLevel;
import crs.client.filehandling.FileHandler;
import crs.course.Lab;
import crs.course.Lecture;

public class AdminClient extends SecureClient {

	private ArgumentsHandler arguments_handler;
	
	private AdminClient(String user_key, SecurityLevel base_level) {
		super(user_key, base_level);
	}

	@Override
	public void getOptions() {
		super.getOptions();
	}

	@Override
	protected SecurityLevel calculateElevationChange(SecurityLevel base_level, String user_key) {
		if (base_level != SecurityLevel.LEVEL_NONE && base_level != SecurityLevel.LEVEL_STUDENT) {
			return SecurityLevel.LEVEL_ADMIN;
		} else {
			return base_level;
		}
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
	
	public void checkSecurity() {
		System.out.println("Current security level is " + security_level.toString());
	}

	public void elevate() {
		this.security_level = SecurityLevel.LEVEL_SYSADMIN;
		checkSecurity();
	}

	public void linkLabAndLecture() {
		System.out.print("Enter Lecture CRN: ");
		int crn = Main.user_input.nextInt();
		Lecture l = (Lecture)(Main.course_registry.searchCourse(crn));
		if (l != null) {
			System.out.print("\nEnter Lab CRN: ");
			int lab_crn = Main.user_input.nextInt();
			Lab lab = (Lab)(Main.course_registry.searchCourse(lab_crn));
			if (lab != null) {
				l.setLinked_section(lab_crn);
				lab.setLinked_section(crn);
			} else
				System.out.println("No lab found matching CRN " + lab_crn);
		} else
			System.out.println("No lecture found matching CRN " + crn);
	}

	private HashMap<String, List<Course>> loadPreReq() {
		return FileHandler.getPrereqs();
	}

	public static AdminClient loadClient(String args[]) {
		String user_key = CRSArgumentsHandler.getParameter("k", "-key", 0);
		SecurityLevel base_level = SecurityLevel.LEVEL_ADMIN;

		AdminClient client = new AdminClient(user_key, base_level);

		client.arguments_handler = new ArgumentsHandler(client.security_level);

		client.pre_req = client.loadPreReq();

		return client;
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
				case Client.OPTION_CHECK_SECURITY:
					System.out.println("Current security level: " + security_level);
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
				case OPTION_LINK_LAB_LECTURE:
					System.out.println("Linking lab and lecture...");
					linkLabAndLecture();
					break;
				case OPTION_PRINT_SCHEDULE:
					System.out.println("Printing schedule...");
					printSchedule();
					break;
				case OPTION_ELEVATE:
					System.out.println("Elevating...");
					elevate();
					break;
				case -1:
					System.err.println("Error getting option in +AdminClient.run(): void");
					Main.user_input.close();
					System.exit(Client.EXIT_FATAL);
					break;
				default:
					System.err.println("Unknown option (" + option + ") in +AdminClient.run(): void");
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

	public ArgumentsHandler getArgumentsHandler() {
		return arguments_handler;
	}

	public void setArgumentsHandler(ArgumentsHandler arguments_handler) {
		this.arguments_handler = arguments_handler;
	}

}