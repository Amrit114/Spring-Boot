package com.products.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.products.dto.ExceptionResposeDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CategoryAlreadyExistsException.class)
	public ResponseEntity<ExceptionResposeDTO> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex,
			WebRequest webRequest){
		
		ExceptionResposeDTO exceptionResposeDTO = new ExceptionResposeDTO(
		webRequest.getDescription(false),
		HttpStatus.CONFLICT,
		ex.getMessage(),
		LocalDateTime.now()
	    );
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResposeDTO);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ExceptionResposeDTO> handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest webRequest){
		ExceptionResposeDTO exceptionResposeDTO = new ExceptionResposeDTO(
				webRequest.getDescription(false),
				HttpStatus.NOT_FOUND,
				ex.getMessage(),
				LocalDateTime.now()
			    );
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResposeDTO);
	}
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ExceptionResposeDTO> handleGlobalException(Exception ex, WebRequest webRequest){
//		ExceptionResposeDTO exceptionResposeDTO = new ExceptionResposeDTO(
//				webRequest.getDescription(false),
//				HttpStatus.INTERNAL_SERVER_ERROR,
//				ex.getMessage(),
//				LocalDateTime.now()
//			    );
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResposeDTO);
//	}
}
