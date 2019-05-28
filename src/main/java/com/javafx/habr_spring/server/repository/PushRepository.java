package com.javafx.habr_spring.server.repository;

import com.javafx.habr_spring.server.domain.PushData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PushRepository extends JpaRepository<PushData, Long> {
    PushData findByCommit(Long id);
    PushData findByCommitAndCommitDate(Long id, Date commitDate);
}
