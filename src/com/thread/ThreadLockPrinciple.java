package com.thread;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 【Java线程】锁机制：synchronized、Lock、Condition<br/>
 * 参考:http://blog.csdn.net/vking_wang/article/details/9952063<br/>
 * @author Admin
 * @version 2016年9月17日
 *
 */
public class ThreadLockPrinciple {

	// 如何让这两个线程有序运行?
	public static void main(String[] args) {
		ThreadLockPrinciple threadLockPrinciple = new ThreadLockPrinciple();

		System.out.println("读写均互斥");
		threadLockPrinciple.t1();

		System.out.println("只有写互斥");
		threadLockPrinciple.t2();

	}

	/**
	 * 多个线程分别读写这个共享数据<br/>
	 * 结果分析:<br/>
	 * R0,R1,R2有序地对数据进行读写,各个线程互不干扰
	 */
	private void t1() {
		final syncData data = new syncData();

		// 写入
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						data.set(new Random().nextInt(30));
					}
				}
			});
			t.setName("Thread-W" + i);
			t.start();
		}
		// 读取
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						data.get();
					}
				}
			});
			t.setName("Thread-R" + i);
			t.start();
		}
	}

	/**
	 * 多个线程分别读写这个共享数据<br/>
	 * 结果分析:<br/>
	 * R0,R1,R2有序地对数据进行写,各个线程互不干扰;<br/>
	 * 在读取时,不存在互斥
	 */
	private void t2() {
		final RwLockData data = new RwLockData();

		// 写入
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						data.set(new Random().nextInt(30));
					}
				}
			});
			t.setName("Thread-W" + i);
			t.start();
		}
		// 读取
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						data.get();
					}
				}
			});
			t.setName("Thread-R" + i);
			t.start();
		}
	}

}

/**
 * <p>
 * java.util.concurrent.lock 中的Lock 框架是锁定的一个抽象，它允许把锁定的实现作为 Java 类，而不是作为语言的特性来实现。
 * 这就为Lock 的多种实现留下了空间，各种实现可能有不同的调度算法、性能特性或者锁定语义。
 * </p>
 * <p>
 * ReentrantLock 类实现了Lock ，它拥有与synchronized相同的并发性和内存语义，
 * 但是添加了类似锁投票、定时锁等候和可中断锁等候的一些特性。 此外，它还提供了在激烈争用情况下更佳的性能 。
 * （换句话说，当许多线程都想访问共享资源时，JVM 可以花更少的时候来调度线程，把更多时间用在执行线程上。）
 * </p>
 */
class Outputter1 {
	private Lock lock = new ReentrantLock();// 锁对象

	public void output(String name) {
		lock.lock(); // 得到锁

		try {
			for (int i = 0; i < name.length(); i++) {
				System.out.print(name.charAt(i));
			}
		} finally {
			lock.unlock();// 释放锁
		}
	}
}

/**
 * <p>
 * Outputter1展示的是和synchronized相同的功能，那Lock的优势在哪里？
 * 例如一个类对其内部共享数据data提供了get()和set()方法，如果用synchronized，则代码如该syncData类所示
 * </p>
 */
class syncData {
	private int data;// 共享数据

	public synchronized void set(int data) {
		System.out.println(Thread.currentThread().getName() + "准备写入数据");
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.data = data;
		System.out.println(Thread.currentThread().getName() + "写入" + this.data);
	}

	public synchronized void get() {
		System.out.println(Thread.currentThread().getName() + "准备读取数据");
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "读取" + this.data);
	}
}

/**
 * 通过ReadWriteLock实现读取线程不互斥,写入线程互斥<br/>
 * 
 * <p>
 * 与互斥锁定相比，读-写锁定允许对共享数据进行更高级别的并发访问。虽然一次只有一个线程（writer
 * 线程）可以修改共享数据，但在许多情况下，任何数量的线程可以同时读取共享数据（reader 线程）
 * 
 * 从理论上讲，与互斥锁定相比，使用读-写锁定所允许的并发性增强将带来更大的性能提高。
 * 
 * 在实践中，只有在多处理器上并且只在访问模式适用于共享数据时，才能完全实现并发性增强。——例如，某个最初用数据填充并且之后不经常对其进行修改的
 * collection，因为经常对其进行搜索（比如搜索某种目录），所以这样的 collection(集合) 是使用读-写锁定的理想候选者。
 * </p>
 */
class RwLockData {
	private int data;// 共享数据
	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	public void set(int data) {
		rwl.writeLock().lock();// 取到写锁
		try {
			System.out.println(Thread.currentThread().getName() + "准备写入数据");
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.data = data;
			System.out.println(Thread.currentThread().getName() + "写入" + this.data);
		} finally {
			rwl.writeLock().unlock();// 释放写锁
		}
	}

	public void get() {
		rwl.readLock().lock();// 取到读锁
		try {
			System.out.println(Thread.currentThread().getName() + "准备读取数据");
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "读取" + this.data);
		} finally {
			rwl.readLock().unlock();// 释放读锁
		}
	}
}

/**
 * <p>
 * 线程间通信Condition <br/>
 * 
 * Condition可以替代传统的线程间通信，用await()替换wait()，用signal()替换notify()，用signalAll()
 * 替换notifyAll()。 <br/>
 * 
 * ——为什么方法名不直接叫wait()/notify()/nofityAll()？因为Object的这几个方法是final的，不可重写！ <br/>
 * 
 * 传统线程的通信方式，Condition都可以实现。 <br/>
 * 
 * 注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。 <br/>
 * 
 * Condition的强大之处在于它可以为多个线程间建立不同的Condition 看JDK文档中的一个例子：假定有一个绑定的缓冲区，它支持 put 和
 * take 方法。如果试图在空的缓冲区上执行 take 操作，则在某一个项变得可用之前，线程将一直阻塞；如果试图在满的缓冲区上执行 put
 * 操作，则在有空间变得可用之前，线程将一直阻塞。我们喜欢在单独的等待 set 中保存put 线程和take
 * 线程，这样就可以在缓冲区中的项或空间变得可用时利用最佳规划，一次只通知一个线程。可以使用两个Condition 实例来做到这一点。 <br/>
 * 
 * ——其实就是java.util.concurrent.ArrayBlockingQueue的功能 <br/>
 * </p>
 * <p>
 * 优点： <br/>
 * 1.假设缓存队列中已经存满，那么阻塞的肯定是写线程，唤醒的肯定是读线程，相反，阻塞的肯定是读线程，唤醒的肯定是写线程。 <br/>
 * 2.那么假设只有一个Condition会有什么效果呢？缓存队列中已经存满，这个Lock不知道唤醒的是读线程还是写线程了，如果唤醒的是读线程，皆大欢喜，
 * 如果唤醒的是写线程，那么线程刚被唤醒，又被阻塞了，这时又去唤醒，这样就浪费了很多时间。
 * </p>
 */
class BoundedBuffer {
	final Lock lock = new ReentrantLock(); // 锁对象
	final Condition notFull = lock.newCondition(); // 写线程锁
	final Condition notEmpty = lock.newCondition(); // 读线程锁

	final Object[] items = new Object[100];// 缓存队列
	int putptr; // 写索引
	int takeptr; // 读索引
	int count; // 队列中数据数目

	// 写
	public void put(Object x) throws InterruptedException {
		lock.lock(); // 锁定
		try {
			// 如果队列满，则阻塞<写线程>
			while (count == items.length) {
				notFull.await();
			}
			// 写入队列，并更新写索引
			items[putptr] = x;
			if (++putptr == items.length)
				putptr = 0;
			++count;

			// 唤醒<读线程>
			notEmpty.signal();
		} finally {
			lock.unlock();// 解除锁定
		}
	}

	// 读
	public Object take() throws InterruptedException {
		lock.lock(); // 锁定
		try {
			// 如果队列空，则阻塞<读线程>
			while (count == 0) {
				notEmpty.await();
			}

			// 读取队列，并更新读索引
			Object x = items[takeptr];
			if (++takeptr == items.length)
				takeptr = 0;
			--count;

			// 唤醒<写线程>
			notFull.signal();
			return x;
		} finally {
			lock.unlock();// 解除锁定
		}
	}
}
