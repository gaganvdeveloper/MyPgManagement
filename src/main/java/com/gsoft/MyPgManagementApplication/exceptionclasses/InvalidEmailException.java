package com.gsoft.MyPgManagementApplication.exceptionclasses;

public class InvalidEmailException extends RuntimeException{
	@Override
	public String getMessage() {
		return "Invalid Email, This Email Is Not Registerd...";
	}
}
