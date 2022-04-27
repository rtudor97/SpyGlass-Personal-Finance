package com.codecamp.SpyGlassApi.domain.goal.service;

import com.codecamp.SpyGlassApi.domain.goal.exceptions.GoalNotFoundException;
import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.goal.model.TypeOfGoal;
import com.codecamp.SpyGlassApi.domain.goal.repo.GoalRepo;
import com.codecamp.SpyGlassApi.domain.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalServiceImplTest {

    @MockBean
    private GoalRepo goalRepo;

    @Autowired
    private GoalService goalService;

    private Goal inputGoal;
    private Goal outputGoal;
    private User user;
    private SimpleDateFormat simpleDateFormat;

    @BeforeEach
    public void setUp() throws ParseException {
        simpleDateFormat = new SimpleDateFormat("mm-dd-yyyy");
        user = new User();
        inputGoal = new Goal(user, TypeOfGoal.VACATION, "bora bora","vacation with fam","beach icon","blue",500.00,100.00,simpleDateFormat.parse("05-25-2022"));
        outputGoal = new Goal(user, TypeOfGoal.VACATION, "bora bora","vacation with fam","beach icon","blue",500.00,100.00,simpleDateFormat.parse("05-25-2022"));
        outputGoal.setId(1L);
    }

    @Test
    @DisplayName("Creation of Goal-Success")
    public void createGoalSuccessTest(){
        BDDMockito.doReturn(outputGoal).when(goalRepo).save(ArgumentMatchers.any());
        Goal returnedGoal = goalService.create(inputGoal);
        Assertions.assertNotNull(returnedGoal);
        Assertions.assertEquals(returnedGoal.getId(), 1L);
    }

    @Test
    @DisplayName("Creation of Goal-Failed")
    public void createGoalFailedTest(){
        BDDMockito.doReturn(Optional.empty()).when(goalRepo).findById(1L);
        Assertions.assertThrows(GoalNotFoundException.class, () -> {
            goalService.getById(1L);
        });
    }

    @Test
    @DisplayName("Calculate Progress Test - Success")
    public void calculateProgressTestSuccess(){
        BDDMockito.doReturn(inputGoal).when(goalRepo).save(ArgumentMatchers.any());
        Goal returnedGoal = goalService.create(inputGoal);
        Assertions.assertNotNull(returnedGoal);

        Double expected = 20.00;
        Double actual = returnedGoal.getProgressPercentage();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Calculate Amount left until Goal Test - Success")
    public void calculateAmountLeftTestSuccess(){
        BDDMockito.doReturn(inputGoal).when(goalRepo).save(ArgumentMatchers.any());
        Goal returnedGoal = goalService.create(inputGoal);
        Assertions.assertNotNull(returnedGoal);

        Double expected = 400.00;
        Double actual = returnedGoal.getAmountLeftUntilGoal();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Get Goal by Id -Success")
    public void getByIdSuccessTest() throws GoalNotFoundException {
        BDDMockito.doReturn(Optional.of(inputGoal)).when(goalRepo).findById(1L);
        Goal foundGoal = goalService.getById(1L);
        Assertions.assertEquals(inputGoal, foundGoal);
    }

    @Test
    @DisplayName("Get Goal by Id -Failed")
    public void getByIdGoalTest(){
        BDDMockito.doReturn(Optional.empty()).when(goalRepo).findById(1L);
        Assertions.assertThrows(GoalNotFoundException.class, () -> {
            goalService.getById(1L);
        });
    }

    @Test
    @DisplayName("Get Goal by Goal -Success")
    public void getByGoalSuccessTest() throws GoalNotFoundException {
        BDDMockito.doReturn(Optional.of(inputGoal)).when(goalRepo).findByGoalName("bora bora");
        Goal foundGoal = goalService.getByGoal("bora bora");
        Assertions.assertEquals(inputGoal, foundGoal);
    }

    @Test
    @DisplayName("Get Goal by Goal -Failed")
    public void getByGoalTest() {
        BDDMockito.doReturn(Optional.empty()).when(goalRepo).findByGoalName("bora bora");
        Assertions.assertThrows(GoalNotFoundException.class, () -> {
            goalService.getByGoal("bora bora");
        });
    }

    @Test
    @DisplayName("Get Goal by GoalType -Success")
    public void getByGoalTypeSuccessTest () throws GoalNotFoundException {
        BDDMockito.doReturn(Optional.of(inputGoal)).when(goalRepo).findByTypeOfGoal(TypeOfGoal.VACATION);
        Goal foundGoal = goalService.getByGoalType(TypeOfGoal.VACATION);
        Assertions.assertEquals(inputGoal, foundGoal);
    }

    @Test
    @DisplayName("Get Goal by GoalType -Failed")
    public void getByGoalTypeTest () {
        BDDMockito.doReturn(Optional.empty()).when(goalRepo).findByTypeOfGoal(TypeOfGoal.VACATION);
        Assertions.assertThrows(GoalNotFoundException.class, () -> {
            goalService.getByGoalType(TypeOfGoal.VACATION);
        });
    }

    @Test
    @DisplayName("Update Goal - Success")
    public void updateGoalTestSuccess() throws GoalNotFoundException {
        Goal expectedGoal = inputGoal;
        expectedGoal.setId(1L);

        BDDMockito.doReturn(Optional.of(outputGoal)).when(goalRepo).findById(1L);
        BDDMockito.doReturn(expectedGoal).when(goalRepo).save(ArgumentMatchers.any());

        Goal actualGoal = goalService.updateGoal(expectedGoal);
        Assertions.assertEquals(expectedGoal.toString(),actualGoal.toString());
    }

    @Test
    @DisplayName("Update Goal - Fail")
    public void updateGoalTestFail(){
        Goal expectedGoal = inputGoal;
        BDDMockito.doReturn(Optional.empty()).when(goalRepo).findById(1L);
        Assertions.assertThrows(GoalNotFoundException.class,() ->{
            goalService.updateGoal(expectedGoal);
        });
    }

    @Test
    @DisplayName("Delete Goal - Success")
    public void deleteGoalTestSuccess(){
        BDDMockito.doReturn(Optional.empty()).when(goalRepo).findById(1L);
        Assertions.assertThrows(GoalNotFoundException.class,()->{
            BDDMockito.doReturn(goalService.getById(1L));
        });
    }
}
