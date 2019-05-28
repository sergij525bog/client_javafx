package com.javafx.habr_spring.server.repository;

import com.javafx.habr_spring.server.domain.ServerProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerProjectRepository extends JpaRepository<ServerProject, Long> {
}
