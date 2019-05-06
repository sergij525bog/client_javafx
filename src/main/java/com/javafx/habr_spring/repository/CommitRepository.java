package com.javafx.habr_spring.repository;

import com.javafx.habr_spring.model.CommitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitRepository extends JpaRepository<CommitData, Long> {
}
