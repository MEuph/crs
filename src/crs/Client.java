package crs;

public interface Client {
	
	final int MIN_OPTION = 0;
	
	final int OPTION_EXIT =				 		0  ;
	final int OPTION_HELP = 						1  ;
	final int OPTION_PRINT_SCHEDULE = 			2  ;
	final int OPTION_ADD_COURSE = 					3  ;
	final int OPTION_DROP_COURSE = 				4  ;
	final int OPTION_SEARCH_COURSE = 				5  ;
	final int OPTION_CHECK_SECURITY = 			6  ;
	final int OPTION_ELEVATE = 					7  ;
	final int OPTION_LINK_LAB_LECTURE = 			8  ;
	
	final int EXIT_SUCCESS = 			 0 ;
	final int EXIT_FATAL = 			 1 ;
	final int NONFATAL_ERROR = 		-1 ;
	final int EXIT_FATAL_UNDEFINED = 	-2 ;

	void exitWithCode(int error_code);
	
	void getOptions();
	void onExit();
	
	void addCourse();
	void dropCourse();
	void searchCourse();
	
	void printSchedule();
	
	int getInput();
	
}