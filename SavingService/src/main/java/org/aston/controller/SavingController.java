package org.aston.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.dto.NewSavingDto;
import org.aston.dto.SavingDTO;
import org.aston.mapper.SavingMapper;
import org.aston.model.Saving;
import org.aston.service.SavingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping("/saving")
public class SavingController {

    private SavingService savingService;

    @PostMapping("/{userId}")
    public SavingDTO create(@PathVariable Integer userId,
                            @RequestBody NewSavingDto newSavingDto) {
        log.info("Получен запрос POST /saving/{}/create", userId);
        Saving saving = SavingMapper.toSaving(newSavingDto);
        return savingService.create(userId, saving);
    }

    @PatchMapping("/{userId}/{savingId}")
    public SavingDTO update(@PathVariable Integer userId,
                          @PathVariable Integer savingId,
                          @RequestBody NewSavingDto newSavingDto) {
        log.info("Получен запрос POST /saving/{}/{}", userId, savingId);
        Saving saving = SavingMapper.toSaving(newSavingDto);
        return savingService.update(userId, savingId, saving);
    }

    @GetMapping("/{savingId}")
    public SavingDTO getSavingById(@PathVariable Integer savingId) {
        log.info("Получен запрос GET /saving/{}", savingId);
        return savingService.getById(savingId);
    }

    @GetMapping("/allByUser/{userId}")
    public List<SavingDTO> getAllSavingsByUserId(@PathVariable Integer userId) {
        log.info("Получен запрос GET /saving/allByUser/{}", userId);
        return savingService.getAllByUserId(userId);
    }

    @DeleteMapping("/{savingId}")
    public void deleteSavingById(@PathVariable Integer savingId) {
        log.info("Получен запрос DELETE /saving/{}", savingId);
        savingService.deleteById(savingId);
    }

    @GetMapping("/all")
    public List<SavingDTO> getAllSavings() {
        log.info("Получен запрос GET /saving/all");
        return savingService.getAllSaving();
    }
}
