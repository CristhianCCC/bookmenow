package com.businessdomain.catalog.exceptions.exceptions;

//business exceptions runtimeExceptions instead of Exceptions to avoid the use of try catch that Exceptions needs to implement

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends RuntimeException {

    //adding the attributes considered as necessary
    private Long id;
    private String code;
    private HttpStatus httpStatus;


    //adding constructors considered as necessary
    public BusinessRuleException(Long id, String code, HttpStatus httpStatus){
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BusinessRuleException (String message, Throwable cause){super (message, cause);}

    public BusinessRuleException(String message){}

    //getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
