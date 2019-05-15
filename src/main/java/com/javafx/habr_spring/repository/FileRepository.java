package com.javafx.habr_spring.repository;

import com.javafx.habr_spring.domain.WriterFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FileRepository extends JpaRepository<WriterFile, Long> {
    @Transactional
    WriterFile findByFilename(String filename);
}
