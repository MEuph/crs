package crs.client;

import crs.CRSArgumentsHandler;
import crs.SecurityLevel;

public class ArgumentsHandler {
	
	public static SecurityLevel security_level;
	
	public ArgumentsHandler(SecurityLevel user_level) {
		ArgumentsHandler.security_level = user_level;
	}

	public static String getParameter(SecurityLevel required_security_level, String key, String alias_key, int index) {
		if (required_security_level == SecurityLevel.LEVEL_NONE) {
			return CRSArgumentsHandler.getParameter(key, alias_key, index);
		} else if (required_security_level == SecurityLevel.LEVEL_STUDENT) {
			if (ArgumentsHandler.security_level != SecurityLevel.LEVEL_NONE) {
				return CRSArgumentsHandler.getParameter(key, alias_key, index);
			} else {
				System.err.println("User with security level " 
					+ ArgumentsHandler.security_level.toString()
					+ " is trying to access parameter that requires "
					+ required_security_level.toString()
				);
				return null;
			}
		} else if (required_security_level == SecurityLevel.LEVEL_ADMIN) {
			if (ArgumentsHandler.security_level == SecurityLevel.LEVEL_ADMIN
					|| ArgumentsHandler.security_level == SecurityLevel.LEVEL_SYSADMIN) {
				return CRSArgumentsHandler.getParameter(key, alias_key, index);
			} else {
				System.err.println("User with security level " 
					+ ArgumentsHandler.security_level.toString()
					+ " is trying to access parameter that requires "
					+ required_security_level.toString()
				);
				return null;
			}
		} else if (required_security_level == SecurityLevel.LEVEL_SYSADMIN
				&& ArgumentsHandler.security_level == SecurityLevel.LEVEL_SYSADMIN) {
			return CRSArgumentsHandler.getParameter(key, alias_key, index);
		} else {
			System.err.println("User with security level " 
				+ ArgumentsHandler.security_level.toString()
				+ " is trying to access parameter that requires "
				+ required_security_level.toString()
			);
			return null;
		}
	}
	
}