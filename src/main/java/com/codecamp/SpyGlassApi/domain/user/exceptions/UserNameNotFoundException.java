package com.codecamp.SpyGlassApi.domain.user.exceptions;

public class UserNameNotFoundException extends Exception {
    public UserNameNotFoundException(String msg){
        super(msg);
    }
}
