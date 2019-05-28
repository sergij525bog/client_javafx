package com.javafx.habr_spring.server.repository;

import com.javafx.habr_spring.server.domain.ServerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServerFileRepository extends JpaRepository<ServerFile, Long> {
    @Transactional
    ServerFile findByFilename(String filename);
}
