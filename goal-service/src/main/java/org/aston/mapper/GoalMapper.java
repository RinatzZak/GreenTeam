package org.aston.mapper;

import org.aston.dto.GoalDto;
import org.aston.dto.NewGoalDto;
import org.aston.model.Goal;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    public static GoalDto toGoalDto(Goal goal) {
        return GoalDto.builder()
                .id(goal.getId())
                .text(goal.getText())
                .price(goal.getPrice())
                .userId(goal.getUserId())
                .build();
    }

    public static Goal toGoal(NewGoalDto newGoalDto) {
        return Goal.builder()
                .text(newGoalDto.getText())
                .price(newGoalDto.getPrice())
                .build();
    }
}
