package com.generic;

/**
 * Java泛型小例子<br/>
 * 参考:http://www.cnblogs.com/lwbqqyumidi/p/3837629.html
 * 
 * @author copy4dev@126.com
 * @date 2016年10月21日
 *
 */
public class GenericTest {

	public static void main(String[] args) {

		/**
		 * java.lang.Number<br/>
		 * 抽象类Number是类BigDecimal，BigInteger，Byte，Double，Float，Integer，
		 * Long和Short的超类。<br/>
		 * Number的子类必须提供将所表示的数字值转换为byte，double，float，int，long和short的方法。
		 */

		Box<String> name = new Box<String>("corn");
		Box<Integer> age = new Box<Integer>(712);
		Box<Number> number = new Box<Number>(3.14);
		
		Box2<Integer> age2 = new Box2<Integer>(369);
		Box2<Number> number2 = new Box2<Number>(1.26);

		getData(name);
		getData(age);
		getData(number);

//		getUpperNumberData(name);// 此处报错
		getUpperNumberData(age);
		getUpperNumberData(number);
		
		getDownerNumberData(age2);
		getDownerNumberData(number2);
	}

	/**
	 * <p>
	 * 类型通配符一般是使用 ?
	 * 代替具体的类型实参。注意了，此处是类型实参，而不是类型形参！且Box<?>在逻辑上是Box<Integer>、Box<Number
	 * >...等所有Box<具体类型实参>的父类。由此，我们依然可以定义泛型方法，来完成此类需求
	 * </p>
	 * 注意:在逻辑上Box<Number>不能视为Box<Integer>的父类
	 */
	public static void getData(Box<?> data) {
		System.out.println("getData :" + data.getData());
	}

	/**
	 * <p>
	 * 定义一个功能类似于getData()的方法，但对类型实参又有进一步的限制：只能是Number类及其子类。此时，需要用到类型通配符上限
	 * </p>
	 * 类型通配符上限通过形如Box<? extends Number>形式定义，相对应的，类型通配符下限为Box<? super
	 * Number>形式，其含义与类型通配符上限正好相反
	 */
	public static void getUpperNumberData(Box<? extends Number> data) {
		System.out.println("getUpperNumberData :" + data.getData());
	}

	/**
	 * 类型通配符下限:<br/>
	 * 自己以及自己的父类
	 */
	public static void getDownerNumberData(Box2<? super Integer> data) {
		System.out.println("getDownerNumberData :" + data.getData());
	}

}

class Box<T> {

	private T data;

	public Box() {

	}

	public Box(T data) {
		setData(data);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}

class Box2<T> {

	private T data;

	public Box2() {

	}

	public Box2(T data) {
		setData(data);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}