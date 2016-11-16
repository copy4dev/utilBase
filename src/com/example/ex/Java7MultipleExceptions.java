package com.example.ex;

/**
 * Java1.7catch异常新特性
 * <p>
 * 如果用一个catch块处理多个异常，可以用管道符（|）将它们分开，在这种情况下异常参数变量（ex）是定义为final的，所以不能被修改。这一特性将生成更少的字节码并减少代码冗余。
 * </p>
 * <p>
 * 另一个升级是编译器对重新抛出异常（rethrown exceptions）的处理。这一特性允许在一个方法声明的throws从句中指定更多特定的异常类型。
 * </p>
 * 参考:http://www.kejilie.com/importnew/article/C781FF9364AEA7EC9D599641D3C0E37F.html
 * 
 * @author 1002360
 * @date 2016年11月15日
 *
 */
public class Java7MultipleExceptions {

	public static void main(String[] args) {
		try {
			rethrow("abc");
		} catch (FirstException | SecondException | ThirdException e) {
			// 以下赋值将会在编译期抛出异常，因为e是final型的
			// e = new Exception();
			System.out.println(e.getMessage());
		}
	}

	static void rethrow(String s) throws FirstException, SecondException, ThirdException {
		try {
			if (s.equals("First"))
				throw new FirstException("First");
			else if (s.equals("Second"))
				throw new SecondException("Second");
			else
				throw new ThirdException("Third");
		} catch (Exception e) {
			// 下面的赋值没有启用重新抛出异常的类型检查功能，这是Java 7的新特性
			// e=new ThirdException();
			throw e;
		}
	}

	static class FirstException extends Exception {

		private static final long serialVersionUID = 1L;

		public FirstException(String msg) {
			super(msg);
		}
	}

	static class SecondException extends Exception {

		private static final long serialVersionUID = 1L;

		public SecondException(String msg) {
			super(msg);
		}
	}

	static class ThirdException extends Exception {

		private static final long serialVersionUID = 1L;

		public ThirdException(String msg) {
			super(msg);
		}
	}

}