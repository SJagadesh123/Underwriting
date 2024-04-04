package com.zettamine.mpa.ucm.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.zettamine.mpa.ucm.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
	
	@ExceptionHandler(CompanyAlreadyExists.class)
	public ResponseEntity<ErrorResponseDto> handleCompanyAlreadyExistsExceptions(
			CompanyAlreadyExists ex, WebRequest webRequest) {
		ErrorResponseDto response = new ErrorResponseDto();

		String apiPath = webRequest.getDescription(false);

		response.setApiPath(apiPath);
		response.setErrorCode(HttpStatus.BAD_REQUEST);
		response.setErrorMessage(ex.getMessage());
		response.setErrorTime(LocalDateTime.now());

		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest webRequest) {
		ErrorResponseDto response = new ErrorResponseDto();

		String apiPath = webRequest.getDescription(false);

		response.setApiPath(apiPath);
		response.setErrorCode(HttpStatus.BAD_REQUEST);
		response.setErrorMessage(ex.getMessage());
		response.setErrorTime(LocalDateTime.now());

		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception ex,
			WebRequest webRequest) {
		ErrorResponseDto response = new ErrorResponseDto();

		String apiPath = webRequest.getDescription(false);

		response.setApiPath(apiPath);
		response.setErrorCode(HttpStatus.BAD_REQUEST);
		response.setErrorMessage(ex.getMessage());
		response.setErrorTime(LocalDateTime.now());

		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(DuplicationException.class)
	public ResponseEntity<ErrorResponseDto> handleDuplicationException(DuplicationException ex,
			WebRequest webRequest) {
		ErrorResponseDto response = new ErrorResponseDto();

		String apiPath = webRequest.getDescription(false);

		response.setApiPath(apiPath);
		response.setErrorCode(HttpStatus.BAD_REQUEST);
		response.setErrorMessage(ex.getMessage());
		response.setErrorTime(LocalDateTime.now());

		return ResponseEntity.badRequest().body(response);
	}
}
