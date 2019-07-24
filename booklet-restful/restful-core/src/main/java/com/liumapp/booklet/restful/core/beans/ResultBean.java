package com.liumapp.booklet.restful.core.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * file ResultBean.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/23
 */
@Data
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = -8145738728023739799L;

	public static final int SUCCESS = 10000;

	/**
	 * 检查产品id异常
	 */
	public static final int CHECK_ID_FAIL = 10001;

	/**
	 * 检查产品名称异常
	 */
	public static final int CHECK_NAME_FAIL = 10002;

	/**
	 * 未知异常
	 */
	public static final int UNKNOWN_EXCEPTION = 99;

	/**
	 * 返回的信息(主要出错的时候使用)
	 */
	private String msg = "success";

	/**
	 * 接口返回码, 0表示成功, 其他看对应的定义
	 * 10000: 成功
	 * >10000 : 表示已知的异常(例如提示错误等, 需要调用地方单独处理)
	 * <10000 : 表示未知的异常(不需要单独处理, 调用方统一处理)
	 */
	private int code = SUCCESS;

	/**
	 * 返回的数据
	 */
	private T data;

	public ResultBean() {
		super();
	}

	public ResultBean(T data) {
		super();
		this.data = data;
	}

	public ResultBean(Throwable e) {
		super();
		this.msg = e.toString();
		this.code = UNKNOWN_EXCEPTION;
	}
}