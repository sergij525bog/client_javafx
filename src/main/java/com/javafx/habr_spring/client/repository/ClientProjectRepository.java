package com.javafx.habr_spring.client.repository;

import com.javafx.habr_spring.client.domain.ClientProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProjectRepository extends JpaRepository<ClientProject, Long> {
}
