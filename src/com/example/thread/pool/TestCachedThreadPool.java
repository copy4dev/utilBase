package com.example.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个可缓存的线程池。<br/>
 * 如果线程池的大小超过了处理任务所需要的线程， 那么就会回收部分空闲（60秒不执行任务）的线程，<br/>
 * 当任务数增加时，此线程池又可以智能的添加新线程来处理任务。<br/>
 * 此线程池不会对线程池大小做限制， 线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。<br/>
 * 可以并发
 */
public class TestCachedThreadPool {

	public static void main(String[] args) {

		// 创建一个可缓存的线程池
		ExecutorService pool = Executors.newCachedThreadPool();

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