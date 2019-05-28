package com.javafx.habr_spring.client.repository;

import com.javafx.habr_spring.client.domain.CommitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CommitRepository extends JpaRepository<CommitData, Long> {
    CommitData findByCommit(Long id);
    CommitData findByCommitAndCommitDate(Long id, Date commitDate);
}
