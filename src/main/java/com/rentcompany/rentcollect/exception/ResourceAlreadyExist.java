package com.rentcompany.rentcollect.exception;


public class ResourceAlreadyExist extends Exception {
	
	private Error error;
	public ResourceAlreadyExist(){
		
	}
	public ResourceAlreadyExist(String code, String msg){
		error = new Error();
		error.setErrorCode(code);
		error.setErrorMessage(msg);
	}
	public Error getError() {
		return error;
	}

	
}