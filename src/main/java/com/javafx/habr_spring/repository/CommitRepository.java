package com.javafx.habr_spring.repository;

import com.javafx.habr_spring.domain.CommitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CommitRepository extends JpaRepository<CommitData, Long> {
    CommitData findByCommit(Long id);
    CommitData findByCommitAndAndCommit_date(Long id, Date commitDate);
}
