package crs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crs.client.AdminClient;
import crs.client.SecureClient;
import crs.client.StudentClient;
import crs.client.filehandling.FileHandler;
import crs.course.CourseRegistry;
import crs.security.CRSSecurity;
import crs.security.Cipher;
import crs.security.Crypto;

public class CRSArgumentsHandler {
	
	public static final Map<String, List<String>> params = new HashMap<>();
	
	public SecureClient loadClientWithArgs(String[] args) {
		CRSArgumentsHandler.loadParameters(args);
		
		FileHandler.addFile("prereqs", CRSArgumentsHandler.getParameter("p", "-prereq", 0));
		FileHandler.addFile("registry", CRSArgumentsHandler.getParameter("r", "-registry", 0));
		
		Main.course_registry = CourseRegistry.loadRegistryFromFile(CRSArgumentsHandler.getParameter("r", "-registry", 0));
		
		Crypto c = new Cipher();
		
		if (getOption("u", "-key")) {
			if (CRSSecurity.validateKey(c, CRSArgumentsHandler.getParameter("u", "-key", 0), CRSSecurity.KEY_LENGTH)) {
				return AdminClient.loadClient(args);
			}
		}
		
		return StudentClient.loadClient(args);
	}
	
	
	public static void loadParameters(String[] args) {
		List<String> options = null;
		for (int i = 0; i < args.length; i++) {
			final String a = args[i];
			
			if (a.charAt(0) == '-') {
				if (a.length() < 2) {
					System.err.println("Error at argument " + a);
					return;
				}
				
				options = new ArrayList<>();
				params.put(a.substring(1), options);
			} else if (options != null) {
				options.add(a);
			} else {
				System.err.println("Illegal parameter usage");
				return;
			}
		}
	}
	
	public static boolean getOption(String key, String alias_key) {
		return params.containsKey(key) || params.containsKey(alias_key);
	}
	
	public static String getParameter(String key, String alias_key, int index) {
		if (params.containsKey(key)) {
			if (index < params.get(key).size())
				return params.get(key).get(index);
			else {
				System.err.println("Error in getParameter(" + key + ", " + alias_key + ", " + index + ")");
				return null;
			}
		} else if (params.containsKey(alias_key)) {
			if (index < params.get(alias_key).size())
				return params.get(alias_key).get(index);
			else {
				System.err.println("Error in getParameter(" + key + ", " + alias_key + ", " + index + ")");
				return null;
			}
		} else {
			return null;
		}
	}
}