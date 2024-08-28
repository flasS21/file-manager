package com.code4fun.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class GlobalException {

	// request-param is missing
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {

		Map<Object, Object> errorResponse = new LinkedHashMap<>();

		errorResponse.put("time-stamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.BAD_REQUEST);
		errorResponse.put("message", ex.getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

	}

	// value of request-param is missing
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<?> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {

		Map<Object, Object> errorResponse = new LinkedHashMap<>();

		errorResponse.put("time-stamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.BAD_REQUEST);
		errorResponse.put("message", ex.getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

	}

	// fileName is missing
	@ExceptionHandler(InvalidFileNameException.class)
	public ResponseEntity<?> handleInvalidFileNameException(InvalidFileNameException ex) {

		Map<Object, Object> errorResponse = new LinkedHashMap<>();

		errorResponse.put("time-stamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.BAD_REQUEST);
		errorResponse.put("message", ex.getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

	}

	// file size is bigger than specified file size in application.properties
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {

		Map<Object, Object> errorResponse = new LinkedHashMap<>();

		errorResponse.put("time-stamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.PAYLOAD_TOO_LARGE);
		errorResponse.put("message", ex.getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(errorResponse);

	}

	// unsupported request type
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {

		Map<Object, Object> errorResponse = new LinkedHashMap<>();

		errorResponse.put("time-stamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.METHOD_NOT_ALLOWED);
		errorResponse.put("message", ex.getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);

	}

	// file is not present
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex) {

		Map<Object, Object> errorResponse = new LinkedHashMap<>();

		errorResponse.put("time-stamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.NOT_FOUND);
		errorResponse.put("message", ex.getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

	}

}
