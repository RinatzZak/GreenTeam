package org.aston.repository;

import org.aston.model.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {

    Optional<Saving> findById(Integer goalId);

    List<Saving> findAllByUserId(Integer userId);

    List<Saving> findAll();

}
