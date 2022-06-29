package com.user.migrate.util;

import java.util.HashMap;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.migrate.dto.ApiResponse;
import com.user.migrate.dto.UserDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, "false", null, null);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserFoundException.class)
	public ResponseEntity<ApiResponse> userFoundExceptionHandler(UserFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, "false", null, null);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(DtoValidationException.class)
	public ResponseEntity<ApiResponse> dtoValidationExceptionHandler(DtoValidationException ex){
		HashMap<Integer, String> errors = new HashMap<>();
		int i = 0;
		for(ConstraintViolation<UserDto> error : ex.getViolations()){
			int fieldName = ++i;
			String message = error.getPropertyPath().toString()+" : "+error.getMessage().toString();
			errors.put(fieldName, message);
		}
		ApiResponse apiResponse = new ApiResponse("Error(s) while validating user details", "false",
				null,//new HashMap<String, UserDto>() {{ put(ex.getUserDto().getUsername(), ex.getUserDto()); }},
				new HashMap<String, HashMap<Integer, String>>() {{ put("errors", errors); }}
		);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
}
