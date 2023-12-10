package crs;

import java.util.Scanner;

import crs.client.SecureClient;
import crs.course.CourseRegistry;

public class Main {
	
	public static SecureClient client;
	
	public static CRSArgumentsHandler args_handler;
	public static CRSErrorHandler error_handler;
	
	public static Scanner user_input;
	
	public static CourseRegistry course_registry;
	
	public static void main(String[] args) {
		user_input = new Scanner(System.in);
		
		args_handler = new CRSArgumentsHandler();
		error_handler = new CRSErrorHandler();
		
		client = args_handler.loadClientWithArgs(args);
		System.out.println();
		
		if (client == null) {
			System.err.println("|DEBUG|+/ main(args: String[]): void|: Client is undefined!");
			System.exit(-1);
		}
		
		while (client.return_code != Client.EXIT_SUCCESS || client.return_code != Client.EXIT_FATAL) {
			client.run();
			client.onExit();
			
			if (client.return_code == Client.EXIT_SUCCESS) {
				System.out.println("Execution completed succesfully...");
				user_input.close();
				System.exit(0);
			} else if (isNonFatal(client.return_code)) {
				String error = error_handler.getError(client.return_code);
				if (error == null) {
					System.err.println("|ERROR SQUARED|: Undefined error code: " + client.return_code);
					client.return_code = Client.EXIT_FATAL;
				} else {
					System.err.println("|DEBUG|ERROR|: +/ Main.main(args: String[]): void -> Nonfatal error reported (code: " + client.return_code + ")");
					System.err.println(error);
				}
			}
		}
		
		if (client.return_code != Client.NONFATAL_ERROR || client.return_code == Client.EXIT_SUCCESS) {
			user_input.close();
			System.exit(client.return_code);
		} else {
			user_input.close();
			System.out.println("Nonfatal error reported");
		}
	}
	
	public static boolean isNonFatal(int return_code) {
		return return_code <= 0;
	}
}