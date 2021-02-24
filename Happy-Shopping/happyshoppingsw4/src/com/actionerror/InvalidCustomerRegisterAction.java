package com.actionerror;

public class InvalidCustomerRegisterAction extends ActionError{
	private String errorMessage;
	public InvalidCustomerRegisterAction(String errorMessage) {
		super(errorMessage);
		this.errorMessage=errorMessage;
	}
	@Override
	public String getError() {
		return this.errorMessage;
	}
}
