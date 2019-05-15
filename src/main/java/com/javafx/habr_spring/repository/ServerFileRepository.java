package com.javafx.habr_spring.repository;

import com.javafx.habr_spring.server.ServerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerFileRepository extends JpaRepository<ServerFile, Long> {
}
