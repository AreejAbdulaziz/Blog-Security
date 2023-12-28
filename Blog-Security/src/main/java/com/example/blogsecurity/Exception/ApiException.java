package com.example.blogsecurity.Exception;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
