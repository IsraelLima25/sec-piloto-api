package com.sec.api.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sec.api.exception.CNPJDuplicateException;
import com.sec.api.exception.EstudanteDuplicateException;
import com.sec.api.exception.RecursoNaoEncontradoException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> argumentNotValid(MethodArgumentNotValidException e, 
			HttpServletRequest request){
		
		ValidationError err = new ValidationError(System.currentTimeMillis(), 
				HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro na Validação dos campos", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<StandarError> recursoNaoEncontrado(RecursoNaoEncontradoException e,
			HttpServletRequest request){
				
		StandarError err = new StandarError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), e.getMessage(), 
				"Recurso não encontrado", request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(CNPJDuplicateException.class)
	public ResponseEntity<StandarError> cnpjDuplicado(CNPJDuplicateException e,
			HttpServletRequest request){
				
		StandarError err = new StandarError(System.currentTimeMillis(),
				HttpStatus.CONFLICT.value(), e.getMessage(), 
				"Está escola já foi cadastrada", request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}
	
	@ExceptionHandler(EstudanteDuplicateException.class)
	public ResponseEntity<StandarError> estudanteDuplicado(EstudanteDuplicateException e,
			HttpServletRequest request){
				
		StandarError err = new StandarError(System.currentTimeMillis(),
				HttpStatus.CONFLICT.value(), e.getMessage(), 
				"Este estudante já foi matriculado", request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}

}
