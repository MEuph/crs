package crs.client;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

import crs.Client;
import crs.Course;
import crs.Main;
import crs.SecurityLevel;

public abstract class SecureClient implements Client {
	
	protected HashMap<String, List<Course>> pre_req;
	
	public SecurityLevel base_level = SecurityLevel.LEVEL_NONE;
	public SecurityLevel security_level;

	public int return_code;
	
	protected String user_key;

	protected int option;
	protected int max_option;
	
	public SecureClient(String user_key, SecurityLevel base_level) {
		this.base_level = base_level;
		this.user_key = user_key;
		
		security_level = this.base_level;
		security_level = calculateElevationChange(security_level, this.user_key);
	}
	
	protected void setOptionRange(int new_max_option) {
		this.max_option = new_max_option;
	}
	
	protected SecurityLevel calculateElevationChange(SecurityLevel base_level, String user_key) {
		if (user_key == "sysadmin") {
			return SecurityLevel.LEVEL_SYSADMIN;
		} else {
			return base_level;
		}
	}

	@Override
	public void getOptions() {
		System.out.println("0.  Exit");
		System.out.println("1.  Help");
		
		setOptionRange(1);
		
		if (security_level == SecurityLevel.LEVEL_STUDENT) {
			System.out.println("2.  Add course");
			System.out.println("3.  Drop course");
			System.out.println("4.  Search for course");
			
			setOptionRange(4);
		} else if (security_level == SecurityLevel.LEVEL_ADMIN) {
			System.out.println("2.  Add course");
			System.out.println("3.  Drop course");
			System.out.println("4.  Search for course");
			System.out.println("5.  Search for student info (R# needed)");
			System.out.println("6.  Check security level");
			System.out.println("7.  Elevate to sysadmin");
			System.out.println("8.  Assign student to course");
			System.out.println("9.  Drop student from course");
	
			setOptionRange(9);
		} else if (security_level == SecurityLevel.LEVEL_SYSADMIN) {
			System.out.println("3.  Add course");
			System.out.println("4.  Drop course");
			System.out.println("5.  Search for student info (R# needed)");
			System.out.println("6.  Check security level");
			System.out.println("7.  Elevate to sysadmin");
			System.out.println("8.  Assign student to course");
			System.out.println("9.  Drop student from course");
			System.out.println("10. Link lecture and lab together");
			System.out.println("11. Change course instructor");
			System.out.println("12. Add TA to course");
			System.out.println("13. Remove TA from course");
			System.out.println("14. Modify course in registry");
			System.out.println("15. Add new course to registry");
			System.out.println("16. Delete course from registry by CRN");
			System.out.println("17. Modify course section by CRN");
			System.out.println("18. Add new course section");
			System.out.println("19. Delete course section by CRN");
			
			setOptionRange(19);
		}
		
		System.out.println("Type man <option_number> for help with an option");
	}

	@Override
	public void onExit() {
		System.err.println("|DEBUG|METHOD_UNIMPLEMENTED|+O SecureClient.onExit(): void");
	}

	@Override
	public int getInput() {
		System.out.print("Option: ");
		try {
			int input = Main.user_input.nextInt();
			if (input > max_option) {
				System.out.println("Highest available option is " + max_option);
				return getInput();
			} else if (input < 0) {
				System.out.println("Lowest available option is " + MIN_OPTION);
				return getInput();
			}
			
			return input;
		} catch (InputMismatchException e) {
			System.err.println("Error in +O SecureClient.getOption(): int");
			return -1;
		} catch (NoSuchElementException e) {
			System.err.println("Error in +O SecureClient.getOption(): int");
			return -1;
		} catch (IllegalStateException e) {
			System.err.println("Error in +O SecureClient.getOption(): int");
			return -1;
		}
	}
	
	public abstract void run();
	
}