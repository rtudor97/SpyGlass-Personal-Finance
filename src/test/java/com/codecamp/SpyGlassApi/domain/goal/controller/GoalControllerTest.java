package com.codecamp.SpyGlassApi.domain.goal.controller;

import com.codecamp.SpyGlassApi.domain.BaseControllerTest;
import com.codecamp.SpyGlassApi.domain.goal.exceptions.GoalNotFoundException;
import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.goal.model.TypeOfGoal;
import com.codecamp.SpyGlassApi.domain.goal.service.GoalService;
import com.codecamp.SpyGlassApi.domain.user.model.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class GoalControllerTest extends BaseControllerTest {

    @MockBean
    private GoalService mockGoalService;

    @Autowired
    private MockMvc mockMvc;

    private Goal inputGoal;
    private Goal mockResponseGoal1;
    private Goal mockResponseGoal2;
    private User user;
    private SimpleDateFormat simpleDateFormat;

    @BeforeEach
    public void setUp() throws ParseException {
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        user = new User("rt@user.com","Ryan","Tudor");
        inputGoal = new Goal(user, TypeOfGoal.VACATION, "bora bora","vacation with fam","beach icon","blue",500.00,100.00,simpleDateFormat.parse("05-25-2022"));
        mockResponseGoal1 = new Goal(user, TypeOfGoal.VACATION, "hawaii","vacation with fam","beach icon","blue",500.00,100.00,simpleDateFormat.parse("05-25-2022"));
        mockResponseGoal1.setId(1L);

        mockResponseGoal2 = new Goal(user, TypeOfGoal.VACATION, "florida","vacation with fam","beach icon","blue",500.00,100.00,simpleDateFormat.parse("05-25-2022"));
        mockResponseGoal2.setId(2L);
    }

    @Test
    @DisplayName("Goal Post: /goal - success")
    public void createGoalRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseGoal1).when(mockGoalService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/goal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputGoal)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goalName", Is.is("hawaii")));
    }

    @Test
    @DisplayName("GET /goal/1 - Found")
    public void getGoalByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseGoal1).when(mockGoalService).getById(1L);

        mockMvc.perform(get("/goal/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.goalName", is("hawaii")));
    }

    @Test
    @DisplayName("Get /goal/1 - Not Found")
    public void getGoalByTitleTestFail() throws Exception {
        BDDMockito.doThrow(new GoalNotFoundException("Goal Not Found")).when(mockGoalService).getById(1L);
        mockMvc.perform(get("/goal/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /goal/1 - Success")
    public void putWidgetTestNotSuccess() throws Exception{
        Goal expectedGoalUpdate = new Goal(user, TypeOfGoal.VACATION, "California","vacation with fam","beach icon","blue",500.00,100.00,simpleDateFormat.parse("05-25-2022"));
        expectedGoalUpdate.setId(1L);
        BDDMockito.doReturn(expectedGoalUpdate).when(mockGoalService).updateGoal(any());
        mockMvc.perform(put("/goal/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputGoal)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.goalName", is("California")));
    }

    @Test
    @DisplayName("PUT /goal/1 - Not Found")
    public void putWidgetTestNotFound() throws Exception{
        BDDMockito.doThrow(new GoalNotFoundException("Not Found")).when(mockGoalService).updateGoal(any());
        mockMvc.perform(put("/goal/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputGoal)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /widgets/1 - Success")
    public void deleteWidgetTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockGoalService).deleteGoal(any());
        mockMvc.perform(delete("/goal/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /widgets/1 - Not Found")
    public void deleteWidgetTestNotFound() throws Exception{
        BDDMockito.doThrow(new GoalNotFoundException("Not Found")).when(mockGoalService).deleteGoal(any());
        mockMvc.perform(delete("/goal/{id}", 1L))
                .andExpect(status().isNotFound());
    }

}
