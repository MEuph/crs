package crs;

public interface Client {
	
	final int MIN_OPTION = 0;
	
	final int OPTION_EXIT =				 		0  ;
	final int OPTION_HELP = 						1  ;
	final int OPTION_ADD_COURSE = 					2  ;
	final int OPTION_DROP_COURSE = 				3  ;
	final int OPTION_SEARCH_COURSE = 				4  ;
	final int OPTION_SEARCH_STUDENT = 			5  ;
	final int OPTION_CHECK_SECURITY = 			6  ;
	final int OPTION_ELEVATE = 					7  ;
	final int OPTION_ASSIGN_COURSE = 				8  ;
	final int OPTION_UNASSIGN_COURSE = 			9  ;
	final int OPTION_LINK_LAB_LECTURE = 			10 ;
	final int OPTION_CHANGE_INSTRUCTOR = 			11 ;
	final int OPTION_ADD_TA = 						12 ;
	final int OPTION_REMOVE_TA = 					13 ;
	final int OPTION_MODIFY_COURSE_REGISTRY = 	14 ;
	final int OPTION_ADD_COURSE_REGISTRY =	 	15 ;
	final int OPTION_DELETE_COURSE_REGISTRY =		16 ;
	final int OPTION_MODIFY_SECTION = 			17 ;
	final int OPTION_ADD_SECTION = 				18 ;
	final int OPTION_DELETE_SECTION = 			19 ;
	
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
	
	int getInput();
	
}