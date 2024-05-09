package org.example.exceptions;

public class NotFoundPersonException extends Exception{
    public NotFoundPersonException(String message){
        super(message);
    }
}
