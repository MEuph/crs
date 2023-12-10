package crs.security;

public class CRSSecurity {

	public static final int KEY_LENGTH = 256;
	
	public static String generateKey(Crypto c, int length) {
		String key = "";
		
		for (int i = 0; i < length - 1; i++) {
			key += (char)(66 + (int)(Math.random() * 24.0));
		}
		
		for (int i = 66; i < 90; i++) {
			String prime_key = key + (char)i;
			if (validateDecryptedKey(c, prime_key, length)) {
				return c.encrypt(prime_key);
			}
		}
		
		return generateKey(c, length);
	}
	
	public static boolean validateKey(Crypto c, String key, int length) {
		if (key.length() != length) return false;
		
		String decrypted = c.decrypt(key);
		
		int sum = 0;
		for (int i = 0; i < decrypted.length() - 1; i++, sum += decrypted.charAt(i));

		return isPrime(sum);
	}
	
	public static boolean validateDecryptedKey(Crypto c, String decrypted, int length) {
		if (decrypted.length() != length) return false;
		
		int sum = 0;
		for (int i = 0; i < decrypted.length() - 1; i++, sum += decrypted.charAt(i));

		return isPrime(sum);
	}
	
	public static boolean isPrime(int i) {
		if (i <= 1) return false;
		
		for (int j = 2; j <= i / 2; j++) {
			if ((i % j) == 0) return false;
		}
		
		return true;
	}
	
}