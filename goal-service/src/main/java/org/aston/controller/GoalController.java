package org.aston.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.dto.GoalDto;
import org.aston.dto.NewGoalDto;
import org.aston.mapper.GoalMapper;
import org.aston.model.Goal;
import org.aston.service.GoalService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping("/goal")
public class GoalController {

    private GoalService goalService;

    @PostMapping("/{userId}")
    public GoalDto create(@PathVariable Integer userId,
                          @RequestBody NewGoalDto newGoalDto) {
        log.info("Получен запрос POST /goal/{}/create", userId);
        Goal goal = GoalMapper.toGoal(newGoalDto);
        return goalService.create(userId, goal);
    }

    @PatchMapping("/{userId}/{goalId}")
    public GoalDto update(@PathVariable Integer userId,
                          @PathVariable Integer goalId,
                          @RequestBody NewGoalDto newGoalDto) {
        log.info("Получен запрос POST /goal/{}/{}", userId, goalId);
        Goal goal = GoalMapper.toGoal(newGoalDto);
        return goalService.update(userId, goalId, goal);
    }

    @GetMapping("/{goalId}")
    public GoalDto getGoalById(@PathVariable Integer goalId) {
        log.info("Получен запрос GET /goal/{}", goalId);
        return goalService.getById(goalId);
    }

    @GetMapping("/all/{userId}")
    public List<GoalDto> getAllGoalsByUserId(@PathVariable Integer userId) {
        log.info("Получен запрос GET /goal/{}", userId);
        return goalService.getAllByUserId(userId);
    }

    @DeleteMapping("/{goalId}")
    public void deleteGoalById(@PathVariable Integer goalId) {
        log.info("Получен запрос DELETE /goal/{}", goalId);
        goalService.deleteById(goalId);
    }
}
