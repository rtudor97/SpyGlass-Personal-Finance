package com.codecamp.SpyGlassApi.domain.goal.service;

import com.codecamp.SpyGlassApi.domain.goal.exceptions.GoalNotFoundException;
import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.goal.model.TypeOfGoal;
import com.codecamp.SpyGlassApi.domain.user.model.User;

public interface GoalService {
    Goal create(Goal goal);
    Goal getById(Long id) throws GoalNotFoundException;
    Goal getByGoal(String name) throws GoalNotFoundException;
    Iterable<Goal> getAllGoals() throws GoalNotFoundException;
    Goal getByGoalType(TypeOfGoal typeOfGoal) throws GoalNotFoundException;
    Goal updateGoal(Goal goal) throws GoalNotFoundException;
    void deleteGoal(Long id) throws GoalNotFoundException;
}
