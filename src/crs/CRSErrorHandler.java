package crs;

public class CRSErrorHandler {

	public String getError(int error_code) {
		switch(error_code) {
		default:
			System.err.println("Unknown error code: " + error_code);
			return null;
		}
	}
	
}
