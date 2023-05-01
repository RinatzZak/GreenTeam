package org.aston.service;

import org.aston.dto.SavingDTO;
import org.aston.model.Saving;

import java.util.List;

public interface SavingService {

    SavingDTO create(Integer userId, Saving saving);

    SavingDTO update(Integer userId, Integer savingId, Saving saving);

    SavingDTO getById(Integer savingId);

    List<SavingDTO> getAllByUserId(Integer userId);

    void deleteById(Integer savingId);

    List<SavingDTO> getAllSaving();

}
