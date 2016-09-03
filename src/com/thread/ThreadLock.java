package com.thread;

/**
 * 线程锁测试
 * 
 * @author copy4dev
 * @date 2016年9月3日
 *
 */
public class ThreadLock implements Runnable {

	private Foo foo = new Foo();

	public static void main(String[] args) {
		ThreadLock r = new ThreadLock();
		Thread ta = new Thread(r, "Thread-A");
		Thread tb = new Thread(r, "Thread-B");
		ta.start();
		tb.start();
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			this.fix(30);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 正常单线程输出的话是:70-40-30
			System.out.println(Thread.currentThread().getName() + " : 当前foo对象的x值= " + foo.getX());
		}
	}

	public int fix(int y) {
		return foo.fix(y);
	}

}

class Foo {
	private int x = 100;

	public int getX() {
		return x;
	}

	public int fix(int y) {
		x = x - y;
		return x;
	}
}