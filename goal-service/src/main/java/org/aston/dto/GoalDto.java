package org.aston.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoalDto {

    private Integer id;

    private String text;

    private Integer price;

    private Integer userId;

}
