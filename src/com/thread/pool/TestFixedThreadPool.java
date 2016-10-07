package com.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建固定大小的线程池。<br/>
 * 每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。<br/>
 * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。<br/>
 * 可以并发运行线程池大小以内的线程
 */
public class TestFixedThreadPool {

	public static void main(String[] args) {

		// 线程池大小
		int poolSize = 2;

		// 创建固定大小的线程池
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);

		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		MyThread t1 = new MyThread("t1");
		MyThread t2 = new MyThread("t2");
		MyThread t3 = new MyThread("t3");
		MyThread t4 = new MyThread("t4");
		MyThread t5 = new MyThread("t5");

		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);

		// 关闭线程池
		pool.shutdown();

	}

}
