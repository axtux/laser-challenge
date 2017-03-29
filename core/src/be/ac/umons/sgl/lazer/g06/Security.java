package be.ac.umons.sgl.lazer.g06;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Easily get hash functions of string.
 */
public class Security {
	/**
	 * md5 Hash calculation of data string.
	 * @param data Data to hash.
	 * @return MD5 hash lower cased string representing of the data string.
	 * Return null if md5 or UTF-8 is not supported by Java implementation.
	 */
	public static String md5(String data) {
		MessageDigest md5;
		byte[] hash;
		try {
			md5 = MessageDigest.getInstance("md5");
			hash = md5.digest(data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		
		return javax.xml.bind.DatatypeConverter.printHexBinary(hash).toLowerCase();
	}
}
