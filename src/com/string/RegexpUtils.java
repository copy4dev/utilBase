package com.string;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexpUtils {

	/**
	 * 以"."截取字符串<br/>
	 * rule: "(.*?)\\."
	 * 
	 * @return
	 */
	public static final ArrayList<String> getStrByPoint(String str) {
		Pattern p = Pattern.compile("(.*?)\\.");
		Matcher m = p.matcher(str);
		ArrayList<String> strs = new ArrayList<String>();
		while (m.find()) {
			strs.add(m.group(1));
		}
		return strs;
	}

}
