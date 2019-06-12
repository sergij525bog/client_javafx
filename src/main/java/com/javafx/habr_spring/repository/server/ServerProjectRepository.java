package com.javafx.habr_spring.repository.server;


import com.javafx.habr_spring.model.server.ServerProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerProjectRepository extends JpaRepository<ServerProject, Long> {
}
