package com.gsoft.MyPgManagementApplication.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidEmailException;
import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidEmailOrPasswordException;
import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidOTPException;
import com.gsoft.MyPgManagementApplication.exceptionclasses.InvalidPhoneOrPasswordException;
import com.gsoft.MyPgManagementApplication.responsestructure.ResponseStructure;

@RestControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(InvalidOTPException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidOTPException(
			InvalidOTPException invalidOTPException) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		structure.setMessage("Invalid OTP Entred, Email Verification Failed....");
		structure.setBody(invalidOTPException.getMessage());
		return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidEmailException(
			InvalidEmailException invalidEmailException) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		structure.setMessage("Invalid Email Entred, This Email is not Assosiated with any Account....");
		structure.setBody(invalidEmailException.getMessage());
		return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(InvalidPhoneOrPasswordException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidPhoneOrPasswordException(
			InvalidPhoneOrPasswordException invalidPhoneOrPasswordException) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		structure.setMessage("Invalid Phone Number or Password, Please Check it");
		structure.setBody(invalidPhoneOrPasswordException.getMessage());
		return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(InvalidEmailOrPasswordException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidEmailOrPasswordException(
			InvalidEmailOrPasswordException invalidEmailOrPasswordException) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		structure.setMessage("Invalid Email or Password, Please Check it");
		structure.setBody(invalidEmailOrPasswordException.getMessage());
		return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
	
	
}
