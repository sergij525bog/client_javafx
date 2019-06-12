package com.javafx.habr_spring.repository.client;

import com.javafx.habr_spring.model.client.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Repository
@Transactional
public interface CommitRepository extends JpaRepository<Commit, Long> {

    //ArrayList<Commit> findByCurrentFileAndCommitDate(Long id, Date commitDate);
    ArrayList<Commit> findByCurrentFile(Long id);
}
