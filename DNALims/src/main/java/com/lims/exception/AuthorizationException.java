/**
 * AuthorizationException.java
 *
 * 2013-5-31
 */
package com.lims.exception;

/**
 * @author lizhihua
 *
 * 权限验证异常
 *
 */
public class AuthorizationException extends Exception {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public AuthorizationException() {
        super();
    }


    public AuthorizationException(String message) {
        super(message);
    }


    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }


    public AuthorizationException(Throwable cause) {
        super(cause);
    }
}
