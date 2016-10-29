package com.example.thread.pool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建一个大小无限的线程池。<br/>
 * 此线程池支持定时以及周期性执行任务的需求。
 */
public class TestScheduledThreadPoolExecutor {

	public static void main(String[] args) {

		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.scheduleAtFixedRate(new Runnable() {// 每隔一段时间就触发异常

					@Override
					public void run() {
						// throw new RuntimeException();
						System.out.println("================");
					}

				}, 1000, 5000, TimeUnit.MILLISECONDS);// 延迟1s，每隔5s执行

		exec.scheduleAtFixedRate(new Runnable() {// 每隔一段时间打印系统时间，证明两者是互不影响的

					@Override
					public void run() {
						System.out.println(System.nanoTime());
					}

				}, 1000, 2000, TimeUnit.MILLISECONDS);

	}

}
