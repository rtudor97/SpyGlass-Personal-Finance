package com.codecamp.SpyGlassApi.domain.goal.repo;

import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.goal.model.TypeOfGoal;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GoalRepo extends CrudRepository<Goal,Long> {
    Optional<Goal> findByGoalName(String name);
    Optional<Goal> findByTypeOfGoal(TypeOfGoal typeOfGoal);
}
