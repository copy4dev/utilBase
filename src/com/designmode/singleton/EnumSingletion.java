package com.designmode.singleton;

/**
 * 使用枚举型单例模式持久化对象<br/>
 * 调用:EnumSingletion.INSTANCE.getInstance()
 * 
 * @author 1002360
 * @date 2016年11月26日
 *
 */
public enum EnumSingletion {

	INSTANCE;

	private Object instance;

	EnumSingletion() {

		// 创建并设置对象
		instance = new Object();

	}

	/**
	 * 获取对象
	 * 
	 * @return singleton object
	 */
	public Object getInstance() {
		return instance;
	}
}
