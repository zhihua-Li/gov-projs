/**
 * AppHandleException.java
 *
 * 2013-5-31
 */
package com.lims.exception;

/**
 * @author lizhihua
 *
 * 控制层异常类
 *
 */
public class AppHandleException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public AppHandleException() {
        super();
    }


    public AppHandleException(String message) {
        super(message);
    }


    public AppHandleException(String message, Throwable cause) {
        super(message, cause);
    }


    public AppHandleException(Throwable cause) {
        super(cause);
    }

}
