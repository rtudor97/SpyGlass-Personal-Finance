package com.codecamp.SpyGlassApi.domain.user.service;

import com.codecamp.SpyGlassApi.domain.goal.exceptions.GoalNotFoundException;
import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.goal.model.TypeOfGoal;
import com.codecamp.SpyGlassApi.domain.goal.service.GoalService;
import com.codecamp.SpyGlassApi.domain.user.exceptions.UserNameNotFoundException;
import com.codecamp.SpyGlassApi.domain.user.exceptions.UserNameTakenException;
import com.codecamp.SpyGlassApi.domain.user.model.User;
import com.codecamp.SpyGlassApi.domain.user.repo.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.doReturn;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepo mockUserRepo;

    @MockBean
    private GoalService goalService;

    @Autowired
    private UserService userService;

    private User inputUser01;
    private User outputUser01;
    private Goal inputGoal01;
    private SimpleDateFormat simpleDateFormat;


    @BeforeEach
    public void setUp() throws ParseException {
        inputUser01 = new User("tsunamiMaxx@gmail.com","Maxx","Blue");
        outputUser01 = new User("tsunamiMaxx@gmail.com","Maxx","Blue");
        outputUser01.setEmail("maxxTsunami@gmail.com");

        simpleDateFormat = new SimpleDateFormat("mm-dd-yyyy");
        inputGoal01 = new Goal(outputUser01, TypeOfGoal.VACATION, "bora bora","vacation with fam","beach icon","blue",500.00,100.00,simpleDateFormat.parse("05-25-2022"));
        goalService.create(inputGoal01);
        List<Goal> goals = new ArrayList<>();
        goals.add(inputGoal01);
        outputUser01.setListOfGoals(goals);
    }

    @Test
    @DisplayName("Creating a User - Success")
    public void createUserAccountTest01() throws UserNameTakenException {
        doReturn(outputUser01).when(mockUserRepo).save(ArgumentMatchers.any());
        User returnedUser = userService.createUser(inputUser01);
        Assertions.assertNotNull(returnedUser);
        Assertions.assertEquals(returnedUser.getEmail(), "tsunamiMaxx@gmail.com");
    }

    @Test
    @DisplayName("User Service findUserByUserName - Success")
    public void findByUserNameSuccess() throws UserNameNotFoundException {
        doReturn(Optional.of(outputUser01)).when(mockUserRepo).findByEmail("tsunamiMaxx@gmail.com");
        User foundUser = userService.findUserByEmail("tsunamiMaxx@gmail.com");
        Assertions.assertEquals(outputUser01.toString(), foundUser.toString());
    }

    @Test
    @DisplayName("User Service findUserByUserName - Fail")
    public void findByUserNameFail(){
        doReturn(Optional.empty()).when(mockUserRepo).findByEmail("tsunamiMaxx@gmail.com");
        Assertions.assertThrows(UserNameNotFoundException.class, () -> {
            userService.findUserByEmail("tsunamiMaxx@gmail.com");});
    }

    @Test
    @DisplayName("User Service: Get All Goals - Success")
    public void getAllGoalsSuccess() throws UserNameNotFoundException, GoalNotFoundException {
        doReturn(Optional.of(outputUser01)).when(mockUserRepo).findByEmail("tsunamiMaxx@gmail.com");
        User foundUser = userService.findUserByEmail("tsunamiMaxx@gmail.com");

        List<Goal> goals = new ArrayList<>();
        goals.add(inputGoal01);
    }

    @Test
    @DisplayName("Delete a User Account - Success")
    public void deleteUserAccount() throws UserNameNotFoundException{
        doReturn(Optional.empty()).when(mockUserRepo).findByEmail("tsunamiMaxx@gmail.com");
        Assertions.assertThrows(UserNameNotFoundException.class, ()-> {
            userService.deleteUserAccount("tsunamiMaxx");
        });
    }
}
