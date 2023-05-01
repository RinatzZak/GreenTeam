package org.aston.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.dto.SavingDTO;
import org.aston.mapper.SavingMapper;
import org.aston.model.Saving;
import org.aston.repository.SavingRepository;
import org.aston.service.SavingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SavingServiceImpl implements SavingService {

    private final SavingRepository repository;

    @Override
    public SavingDTO create(Integer userId, Saving saving) {

        saving.setUserId(userId);
        Saving newSaving = repository.save(saving);
        log.info("Сбережение с id = {} создана", newSaving.getId());
        return SavingMapper.toSavingDTO(newSaving);
    }

    @Override
    public SavingDTO update(Integer userId, Integer savingId, Saving saving) {

        Saving updatedSaving = repository.findById(savingId).orElseThrow(() -> new EntityNotFoundException("Сбережение не " +
                "может быть обновлено, т.к. его нет в базе"));
        if (saving.getInRubles() != null) {
            updatedSaving.setInRubles(saving.getInRubles());
        }
        if (saving.getInDollars() != null) {
            updatedSaving.setInDollars(saving.getInDollars());
        }
        if (saving.getInEuro() != null) {
            updatedSaving.setInEuro(saving.getInEuro());
        }
        repository.save(updatedSaving);
        log.info("Сбережение с id = {} обновлено", updatedSaving.getId());
        return SavingMapper.toSavingDTO(updatedSaving);
    }

    @Override
    public SavingDTO getById(Integer savingId) {

        Saving saving = repository.findById(savingId).orElseThrow(() -> new EntityNotFoundException(String.format(
                "Сбережение с id = %d не найдено", savingId)));
        log.info("Сбережение с id = {} получено", saving.getId());
        return SavingMapper.toSavingDTO(saving);

    }

    @Override
    public List<SavingDTO> getAllByUserId(Integer userId) {

        List<Saving> savings = repository.findAllByUserId(userId);
        log.info("Получен список сбережений пользователя с id = {}", userId);
        return savings.stream()
                .map(SavingMapper::toSavingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer savingId) {

        repository.findById(savingId).orElseThrow(() -> new EntityNotFoundException("Сбережение не " +
                "может быть удалено, т.к. его нет в базе"));
        repository.deleteById(savingId);
        log.info("Сбережение с id = {} удалено", savingId);

    }

    @Override
    public List<SavingDTO> getAllSaving() {

        List<Saving> savings = repository.findAll();
        log.info("Получен весь список сбережений");
        return savings.stream()
                .map(SavingMapper::toSavingDTO)
                .collect(Collectors.toList());
    }
}
