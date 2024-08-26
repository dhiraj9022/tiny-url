package com.urlshortner.exception;

public class UnAuthException extends RuntimeException{
    public UnAuthException(String message){
        super(message);
    }
}
