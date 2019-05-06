package com.javafx.habr_spring.repository;

import com.javafx.habr_spring.domain.WriterFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<WriterFile, Long> {
}
