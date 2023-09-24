package com.project.ebankingbackend.exceptions.handler;

import com.project.ebankingbackend.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    private ResponseEntity<ErrorResponse> handeException(CustomerNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse("customer not found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmailAlreadyExistException.class)
    private ResponseEntity<ErrorResponse> handeException(EmailAlreadyExistException emailAlreadyExistException){
        ErrorResponse errorResponse = new ErrorResponse("Email already used",HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CustomerAlreadyExistException.class)
    private ResponseEntity<ErrorResponse> handeException(CustomerAlreadyExistException customerAlreadyExistException){
        ErrorResponse errorResponse = new ErrorResponse("Customer already Exist",HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = OverBalanceException.class)
    private ResponseEntity<ErrorResponse> handeException(OverBalanceException overBalanceException){
        ErrorResponse errorResponse = new ErrorResponse("You can't debit this amount",HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = BankAccountNotFoundException.class)
    private ResponseEntity<ErrorResponse> handeException(BankAccountNotFoundException exception ){
        ErrorResponse errorResponse = new ErrorResponse("bank account not found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


}
