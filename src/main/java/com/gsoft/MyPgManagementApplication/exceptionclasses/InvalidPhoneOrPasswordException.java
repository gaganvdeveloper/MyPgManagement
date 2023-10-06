package com.gsoft.MyPgManagementApplication.exceptionclasses;

public class InvalidPhoneOrPasswordException extends RuntimeException{

	@Override
	public String getMessage() {
		return "Invalid Phone Number or Password";
	}
}
