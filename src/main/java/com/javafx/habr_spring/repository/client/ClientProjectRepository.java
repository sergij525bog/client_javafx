package com.javafx.habr_spring.repository.client;

import com.javafx.habr_spring.model.client.ClientProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProjectRepository extends JpaRepository<ClientProject, Long> {
}
