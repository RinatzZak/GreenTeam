package org.aston.mapper;


import org.aston.dto.NewSavingDto;
import org.aston.dto.SavingDTO;
import org.aston.model.Saving;
import org.springframework.stereotype.Component;

@Component
public class SavingMapper {

    public static SavingDTO toSavingDTO(Saving saving) {
        return SavingDTO.builder()
                .id(saving.getId())
                .inRubles(saving.getInRubles())
                .inDollars(saving.getInDollars())
                .inEuro(saving.getInEuro())
                .userId(saving.getUserId())
                .build();
    }

    public static Saving toSaving(NewSavingDto newSavingDto) {
        return Saving.builder()
                .inRubles(newSavingDto.getInRubles())
                .inDollars(newSavingDto.getInDollars())
                .inEuro(newSavingDto.getInEuro())
                .build();
    }


}
