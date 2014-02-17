package com.lims.util.crypt;

public class CryptException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 78368540095055179L;

	public CryptException(){
		super();
	}

	public CryptException(String message){
		super(message);
	}

	public CryptException(Throwable t){
		super(t);
	}

}
