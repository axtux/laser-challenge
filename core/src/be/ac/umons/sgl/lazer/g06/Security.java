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
	 * @return MD5 hash lower cased string representing of the data string
	 */
	public static String md5(String data) {
		MessageDigest md5;
		byte[] hash;
		try {
			md5 = MessageDigest.getInstance("md5");
			hash = md5.digest(data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		return javax.xml.bind.DatatypeConverter.printHexBinary(hash).toLowerCase();
	}
}
