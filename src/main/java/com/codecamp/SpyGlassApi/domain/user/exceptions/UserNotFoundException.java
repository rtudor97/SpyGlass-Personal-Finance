package com.codecamp.SpyGlassApi.domain.user.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg){
        super(msg);
    }
}
