package com.codecamp.SpyGlassApi.domain.goal.controller;

import com.codecamp.SpyGlassApi.domain.goal.exceptions.GoalNotFoundException;
import com.codecamp.SpyGlassApi.domain.goal.model.Goal;
import com.codecamp.SpyGlassApi.domain.goal.model.TypeOfGoal;
import com.codecamp.SpyGlassApi.domain.goal.service.GoalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goal")
@Slf4j
public class GoalController {

    private GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService){
        this.goalService = goalService;
    }

    @PostMapping("")
    public ResponseEntity<Goal> create(@RequestBody Goal goal){
        goal = goalService.create(goal);
        return new ResponseEntity<>(goal,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> requestGoalById(@PathVariable Long id) throws GoalNotFoundException {
        Goal response = goalService.getById(id);
        log.info(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{goal}")
    public ResponseEntity<Goal> requestGoalByName(@PathVariable String name) throws GoalNotFoundException {
        Goal response = goalService.getByGoal(name);
        log.info(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{goalType}")
    public ResponseEntity<Goal> requestGoalByType(@PathVariable TypeOfGoal typeOfGoal) throws GoalNotFoundException {
        Goal response = goalService.getByGoalType(typeOfGoal);
        log.info(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Goal>> getAll() throws GoalNotFoundException {
        Iterable<Goal> all = goalService.getAllGoals();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody Goal goal) {
        try {
            goal = goalService.updateGoal(goal);
            return new ResponseEntity<>(goal, HttpStatus.OK);
        } catch (GoalNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            goalService.deleteGoal(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }catch (GoalNotFoundException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}

