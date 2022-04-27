package com.codecamp.SpyGlassApi.domain.user.service;

import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.user.exceptions.UserNameNotFoundException;
import com.codecamp.SpyGlassApi.domain.user.exceptions.UserNameTakenException;
import com.codecamp.SpyGlassApi.domain.user.exceptions.UserNotFoundException;
import com.codecamp.SpyGlassApi.domain.user.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user) throws UserNameTakenException;
    User findUserByEmail(String email) throws UserNameNotFoundException;
    List<Goal> getAllGoalsForUser(User user) throws UserNotFoundException;
    void deleteUserAccount(String userName) throws UserNameNotFoundException;
    User assignGoalsToUser(User user, Goal goals) throws UserNameNotFoundException;


}
