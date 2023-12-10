package crs.client;

import java.util.HashMap;
import java.util.List;

import crs.CRSArgumentsHandler;
import crs.Client;
import crs.Course;
import crs.Main;
import crs.SecurityLevel;
import crs.client.filehandling.FileHandler;

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
		// TODO: Unimplemented method addCourse()
	}

	@Override
	public void dropCourse() {
		// TODO: Unimplemented method dropCourse()
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

	public void searchStudent() {
		// TODO: Unimplemented method searchStudent()
	}

	public void checkSecurity() {
		// TODO: Unimplemented method checkSecurity()
	}

	public void elevate() {
		// TODO: Unimplemented method elevate()
	}

	public void assignCourse() {
		// TODO: Unimplemented method assignCourse()
	}

	public void unassignCourse() {
		// TODO: Unimplemented method unassignCourse()
	}

	public void linkLabAndLecture() {
		// TODO: Unimplmeneted method linkLabAndLecture()
	}

	public void changeInstructor() {
		// TODO: Unimplemented method changeInstructor()
	}

	public void addTA() {
		// TODO: Unimplemented method addTA()
	}

	public void removeTA() {
		// TODO: Unimplemented method removeTA()
	}

	public void modifyCourseRegistry() {
		// TODO: Unimplemented method modifyCourseRegistry()
	}

	public void addCourseRegistry() {
		// TODO: Unimplemented method addCourseRegistry()
	}

	public void deleteCourseRegistry() {
		// TODO: Unimplemented method deleteCourseRegistry()
	}

	public void modifySection() {
		// TODO: Unimplemented method modifySection()
	}

	public void addSection() {
		// TODO: Unimplemented method addSection()
	}

	public void deleteSection() {
		// TODO: Unimplemented method deleteSection()
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
		
		System.out.println(client.pre_req.toString());

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
				case OPTION_SEARCH_STUDENT:
					System.out.println("Searching for a student...");
					searchStudent();
					break;
				case OPTION_ELEVATE:
					System.out.println("Elevating to sysadmin...");
					elevate();
					break;
				case OPTION_ASSIGN_COURSE:
					System.out.println("Adding student to course...");
					assignCourse();
					break;
				case OPTION_UNASSIGN_COURSE:
					System.out.println("Dropping student from course...");
					unassignCourse();
					break;
				case OPTION_LINK_LAB_LECTURE:
					System.out.println("Linking lab and lecture...");
					linkLabAndLecture();
					break;
				case OPTION_CHANGE_INSTRUCTOR:
					System.out.println("Changing course instructor...");
					changeInstructor();
					break;
				case OPTION_ADD_TA:
					System.out.println("Adding TA to course...");
					addTA();
					break;
				case OPTION_REMOVE_TA:
					System.out.println("Removing TA from course...");
					removeTA();
					break;
				case OPTION_MODIFY_COURSE_REGISTRY:
					System.out.println("Modifying course in registry...");
					modifyCourseRegistry();
					break;
				case OPTION_ADD_COURSE_REGISTRY:
					System.out.println("Adding course to registry...");
					addCourseRegistry();
					break;
				case OPTION_DELETE_COURSE_REGISTRY:
					System.out.println("Deleting course from registry...");
					deleteCourseRegistry();
					break;
				case OPTION_MODIFY_SECTION:
					System.out.println("Modifying course section...");
					modifySection();
					break;
				case OPTION_ADD_SECTION:
					System.out.println("Adding section of course...");
					addSection();
					break;
				case OPTION_DELETE_SECTION:
					System.out.println("Deleting section of course...");
					deleteSection();
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