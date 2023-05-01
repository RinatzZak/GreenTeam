package org.aston.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingDTO {

    private Integer id;

    private Double inRubles;

    private Double inDollars;

    private Double inEuro;

    private Integer userId;

}
