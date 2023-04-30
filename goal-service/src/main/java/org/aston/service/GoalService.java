package org.aston.service;

import org.aston.dto.GoalDto;
import org.aston.model.Goal;

import java.util.List;

public interface GoalService {

    GoalDto create(Integer userId, Goal goal);

    GoalDto update(Integer userId, Integer goalId, Goal goal);

    GoalDto getById(Integer goalId);

    List<GoalDto> getAllByUserId(Integer userId);

    void deleteById(Integer goalId);
}
