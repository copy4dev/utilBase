package com.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionUtil {

	public static void main(String[] args) {

		String string = "132";

		try {

			System.out.println(getMD5(string));
			System.out.println(getMD5(string, true));

			System.out.println(getBase64Decode(string));

			System.out.println(getTimeSpan(new Date()));

			System.out.println(getSHA1(string));

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取MD5密文
	 * 
	 * @param src
	 * @param isCase 是否转小写字母
	 * @return
	 */
	public static String getMD5(String src, boolean isCase) {
		if (isCase)
			return DigestUtils.md5Hex(src);
		else
			return DigestUtils.md5Hex(src).toUpperCase();
	}

	/**
	 * 获取MD5密文
	 * 
	 * @param src
	 * @return
	 * @throws NoSuchAlgorithmException 算法获取失败
	 * @throws UnsupportedEncodingException
	 */
	public static String getMD5(String src) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		byte[] btInput = src.getBytes();
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();

		// 把密文转换成十六进制的字符串形式
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

	/**
	 * 获取Base64密文
	 * 
	 * @param src
	 * @return
	 */
	public static String getBase64Decode(String src) {
		return Base64.encodeBase64URLSafeString(src.getBytes());
	}

	/**
	 * 获取时间戳
	 */
	public static long getTimeSpan(Date time) {
		return System.currentTimeMillis();
	}

	/**
	 * 获取SHA1密文
	 * 
	 * @param src
	 * @return
	 */
	public static String getSHA1(String src) {
		return DigestUtils.sha1Hex(src);
	}

}
