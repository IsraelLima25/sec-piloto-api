package com.sec.api.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> listError = new ArrayList<>();

	public ValidationError(Long timeStamp, Integer status, String error, String msg, String path) {
		super(timeStamp, status, error, msg, path);
	}

	public List<FieldMessage> getListError() {
		return listError;
	}

	public void addError(String fieldMessage, String message) {
		listError.add(new FieldMessage(fieldMessage, message));
	}

}
