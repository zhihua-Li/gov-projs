/**
 * ServiceException.java
 *
 * 2013-5-26
 */
package com.lims.exception;

/**
 * @author lizhihua
 *
 * service层异常
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public ServiceException() {
        super();
    }


    public ServiceException(String message) {
        super(message);
    }


    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }


    public ServiceException(Throwable cause) {
        super(cause);
    }
}
