package org.aston.repository;

import org.aston.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findAllByUserId(Integer userId);

    Optional<Goal> findById(Integer goalId);
}
