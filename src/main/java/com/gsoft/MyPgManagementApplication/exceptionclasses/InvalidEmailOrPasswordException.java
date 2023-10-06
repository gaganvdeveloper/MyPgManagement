package com.gsoft.MyPgManagementApplication.exceptionclasses;

public class InvalidEmailOrPasswordException extends RuntimeException{
	@Override
	public String getMessage() {
		return "Invalid Email or Password, Please Check it..";
	}
	
}
