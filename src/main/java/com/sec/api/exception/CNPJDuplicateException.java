package com.sec.api.exception;

public class CNPJDuplicateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CNPJDuplicateException(String msg) {
		super(msg);
	}

}
