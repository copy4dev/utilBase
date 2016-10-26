package com.algorithm;

import java.text.ParseException;

/**
 * 用句柄控制数组的返回<br/>
 * 参考:Java编程思想-第四版
 * 
 * @author copy4dev
 * @date 2016年10月26日
 *
 */
public class ReturnArray {

	static String[] flav = { "Chocolate", "Strawberry", "Vanilla Fudge Swirl", "Mint Chip", "Mocha Almond Fudge", "Rum Raisin", "Praline Cream", "Mud Pie" };

	static String[] flavorSet(int n) {
		String[] results = new String[n];
		int[] picks = new int[n];
		for (int i = 0; i < picks.length; i++)
			picks[i] = -1;
		for (int i = 0; i < picks.length; i++) {

			// 使用while无限循环查找数组元素?
			retry: while (true) {
				int t = (int) (Math.random() * flav.length);
				for (int j = 0; j < i; j++)

					if (picks[j] == t)
						continue retry;
				picks[i] = t;
				results[i] = flav[t];
				break;
			}
		}
		return results;
	}

	public static void main(String[] args) throws ParseException {

		for (int i = 0; i < 20; i++) {
			System.out.println("flavorSet(" + i + ") = ");
			String[] fl = flavorSet(flav.length);
			for (int j = 0; j < fl.length; j++)
				System.out.println("\t" + fl[j]);
		}

	}

}
