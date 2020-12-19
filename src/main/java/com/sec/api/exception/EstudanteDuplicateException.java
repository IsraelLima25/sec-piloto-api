package com.sec.api.exception;

public class EstudanteDuplicateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EstudanteDuplicateException(String msg) {
		super(msg);
	}
}
