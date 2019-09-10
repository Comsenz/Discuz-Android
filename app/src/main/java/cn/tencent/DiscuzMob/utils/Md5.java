package cn.tencent.DiscuzMob.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility class for computing MD5 hashes.
 * 
 * @author Cyril Mottier
 */
public class Md5 {

	private static MessageDigest sMd5MessageDigest;
	static {
		try {
			sMd5MessageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
	}

	private Md5() {
	}

	public static String md5(String s) {

		if (sMd5MessageDigest == null) {
			throw new IllegalArgumentException("MD5 creator is null");
		}
		sMd5MessageDigest.reset();
		sMd5MessageDigest.update(s.getBytes());
		final byte digest[] = sMd5MessageDigest.digest();
		return byteToHex(digest);
	}

	private static String byteToHex(byte[] bytes) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < bytes.length; n++) {
			stmp = (Integer.toHexString(bytes[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}
}
