package com.rentcompany.rentcollect.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void handleAllExceptions(Exception e){
		System.out.println("CustomExceptionHandler.handleAllExceptions() "+e.getMessage());
	}
	
	@ExceptionHandler(ResourceAlreadyExist.class)
	public ResponseEntity<Object> handleResourceAlreadyExists(ResourceAlreadyExist resourceAlreadyExist){
		System.out.println("CustomExceptionHandler.handleResourceAlreadyExists()");
		Error error =  resourceAlreadyExist.getError();
		return new ResponseEntity<Object>(error, HttpStatus.FOUND);
	}
}

