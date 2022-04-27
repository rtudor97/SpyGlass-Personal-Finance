package com.codecamp.SpyGlassApi.domain.goal.service;

import com.codecamp.SpyGlassApi.domain.goal.exceptions.GoalNotFoundException;
import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.goal.model.TypeOfGoal;
import com.codecamp.SpyGlassApi.domain.goal.repo.GoalRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class GoalServiceImpl implements GoalService{

    private GoalRepo goalRepo;

    @Autowired
    public GoalServiceImpl(GoalRepo goalRepo){
        this.goalRepo = goalRepo;
    }

    @Override
    public Goal create(Goal goal) {
        calculateProgress(goal);
        return goalRepo.save(goal);
    }

    @Override
    public Goal getById(Long id) throws GoalNotFoundException {
        Optional<Goal> goalOptional = goalRepo.findById(id);
        if(goalOptional.isEmpty())
            throw new GoalNotFoundException("Goal Not Found");
        return goalOptional.get();
    }

    @Override
    public Goal getByGoal(String name) throws GoalNotFoundException {
        Optional<Goal> goalOptional = goalRepo.findByGoalName(name);
        if(goalOptional.isEmpty())
            throw new GoalNotFoundException("Goal Not Found");
        return goalOptional.get();
    }

    @Override
    public Iterable<Goal> getAllGoals() throws GoalNotFoundException {
        return goalRepo.findAll();
    }

    @Override
    public Goal getByGoalType(TypeOfGoal typeOfGoal) throws GoalNotFoundException {
        Optional<Goal> goalOptional = goalRepo.findByTypeOfGoal(typeOfGoal);
        if(goalOptional.isEmpty())
            throw new GoalNotFoundException("Goal Not Found");
        return goalOptional.get();
    }

    @Override
    public Goal updateGoal(Goal goal) throws GoalNotFoundException {
        Long id = goal.getId();
        Optional<Goal> goalOptional = goalRepo.findById(id);
        if(goalOptional.isEmpty()){
            throw new GoalNotFoundException("Goal not found");
        }
        Goal goalUpdated = goalOptional.get();
        calculateProgress(goalUpdated);
        return goalRepo.save(goalUpdated);
    }

    @Override
    public void deleteGoal(Long id) throws GoalNotFoundException {
        Optional<Goal> goalOptional = goalRepo.findById(id);
        if(goalOptional.isEmpty()){
            throw new GoalNotFoundException("Goal with ID: " + id + "not found");
        }
        Goal goalToRemove = goalOptional.get();
        goalRepo.delete(goalToRemove);
    }

    private static void calculateProgress(Goal goal){
        Double amountAlreadySaved = goal.getAmountAlreadySaved();
        Double targetSavingsAmount = goal.getTargetSavingsAmount();
        Double decimal = (amountAlreadySaved / targetSavingsAmount) * 100;
        goal.setProgressPercentage(decimal);

        Double amountLeft = targetSavingsAmount - amountAlreadySaved;
        goal.setAmountLeftUntilGoal(amountLeft);
        log.info("ProgressBar complete for goal");
    }

}
