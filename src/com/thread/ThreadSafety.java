package com.thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 线程安全类<br/>
 * 参考:http://lavasoft.blog.51cto.com/62575/99155/
 * @author Admin
 * @version 2016年9月18日
 *
 */
public class ThreadSafety {

	/**
	 * 一个形象的例子，比如一个集合是线程安全的，有两个线程在操作同一个集合对象。<br/>
	 * 当第一个线程查询集合非空后，删除集合中所有元素的时候，第二个线程也来执行与第一个线程相同的操作。<br/>
	 * 也许在第一个线程查询后，第二个线程也查询出集合非空。<br/>
	 * 但是当第一个执行清除后，第二个再执行删除显然是不对的，因为此时集合已经为空了。
	 */
	public static void main(String[] args) {

		final boolean isSyn = true;
		final StringList sl = new StringList();

		if (isSyn)
			sl.synAdd("admin");
		else
			sl.add("admin");

		@SuppressWarnings("hiding")
		class StringDropper extends Thread {

			String name;

			public StringDropper(String name) {
				this.name = name;
			}

			public void run() {
				String str = "";
				if (isSyn)
					str = sl.synRemoveFirst();
				else
					str = sl.removeFirst();
				System.out.println(name + " remove: " + str);
			}
		}

		Thread t1 = new StringDropper("t1");
		Thread t2 = new StringDropper("t2");
		t1.start();
		t2.start();
	}

	class StringDropper extends Thread {

		private StringList sl = new StringList();
		private String name;

		public StringDropper(String name) {
			this.name = name;
		}

		public void run() {
			String str = sl.removeFirst();
			System.out.println(name + " remove: " + str);
		}
	}

}

/**
 * 当一个类已经很好的同步以保护它的数据时，这个类就称为“线程安全的”。<br/>
 * 即使是线程安全类，也应该特别小心，因为操作的线程是间仍然不一定安全。<br/>
 * <p>
 * 虽然集合对象 private List nameList = Collections.synchronizedList(new
 * LinkedList()); 是同步的，但是程序还不是线程安全的。
 * 出现这种事件的原因是，法一中一个线程操作列表过程中无法阻止另外一个线程对列表的其他操作。
 * </p>
 * 法二解决了法一的不足
 */
class StringList {
	private List<String> strList = Collections.synchronizedList(new LinkedList<String>());

	public int getSize() {
		return strList.size();
	}

	// --- 法一 start---
	public void add(String str) {
		strList.add(str);
	}

	public String removeFirst() {
		if (strList.size() > 0) {
			return (String) strList.remove(0);
		} else {
			return "emp";
		}
	}

	// --- 法一 end ---

	// --- 法二 start ---
	public synchronized void synAdd(String str) {
		strList.add(str);
	}

	public synchronized String synRemoveFirst() {
		if (strList.size() > 0) {
			return (String) strList.remove(0);
		} else {
			return "emp";
		}
	}
	// --- 法二 end ---
}
