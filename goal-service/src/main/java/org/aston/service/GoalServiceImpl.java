package org.aston.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.dto.GoalDto;
import org.aston.mapper.GoalMapper;
import org.aston.model.Goal;
import org.aston.repository.GoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    @Override
    public GoalDto create(Integer userId, Goal goal) {
        goal.setUserId(userId);
        Goal createdGoal = goalRepository.save(goal);
        log.info("Цель с id = {} создана", createdGoal.getId());
        return GoalMapper.toGoalDto(createdGoal);
    }

    @Override
    public GoalDto update(Integer userId, Integer goalId, Goal goal) {
        Goal updatedGoal = goalRepository.findById(goalId).orElseThrow(() -> new EntityNotFoundException("Цель не " +
                "может быть обновлена, т.к. её нет в базе"));
        if (goal.getText() != null) {
            updatedGoal.setText(goal.getText());
        }
        if (goal.getPrice() != null) {
            updatedGoal.setPrice(goal.getPrice());
        }
        goalRepository.save(updatedGoal);
        log.info("Цель с id = {} обновлена", updatedGoal.getId());
        return GoalMapper.toGoalDto(updatedGoal);
    }

    @Override
    public GoalDto getById(Integer goalId) {
        Goal gotGoal = goalRepository.findById(goalId).orElseThrow(() -> new EntityNotFoundException(String.format(
                "Цель с id = %d не найдена", goalId)));
        log.info("Цель с id = {} получена", gotGoal.getId());
        return GoalMapper.toGoalDto(gotGoal);
    }

    @Override
    public List<GoalDto> getAllByUserId(Integer userId) {
        List<Goal> goals = goalRepository.findAllByUserId(userId);
        log.info("Получен список для целей пользователя с id = {}", userId);
        return goals.stream()
                .map(GoalMapper::toGoalDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer goalId) {
        goalRepository.findById(goalId).orElseThrow(() -> new EntityNotFoundException("Цель не " +
                "может быть удалена, т.к. её нет в базе"));
        goalRepository.deleteById(goalId);
        log.info("Цель с id = {} удалена", goalId);
    }
}
