package com.codecamp.SpyGlassApi.domain.user.exceptions;

public class UserNameTakenException extends Exception{
    public UserNameTakenException(String msg){
        super(msg);
}
}
