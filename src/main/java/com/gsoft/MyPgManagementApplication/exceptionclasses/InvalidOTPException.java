package com.gsoft.MyPgManagementApplication.exceptionclasses;

public class InvalidOTPException extends RuntimeException{
	@Override
	public String getMessage() {
		return "Email Verification Failed... Due to Un-Authorised OTP";
	}
}
