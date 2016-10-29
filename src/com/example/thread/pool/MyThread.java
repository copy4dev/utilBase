package com.example.thread.pool;

public class MyThread implements Runnable {

	private String objName;

	public MyThread(String objName) {
		this.objName = objName;
	}

	@Override
	public void run() {

		try {
			Thread.sleep(1000 * 3);
			System.out.println(Thread.currentThread().getName() + " : "
					+ objName + " is running ...");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
