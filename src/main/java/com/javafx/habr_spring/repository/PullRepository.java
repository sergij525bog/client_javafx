package com.javafx.habr_spring.repository;

import com.javafx.habr_spring.server.PullData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PullRepository extends JpaRepository<PullData, Long> {
}
