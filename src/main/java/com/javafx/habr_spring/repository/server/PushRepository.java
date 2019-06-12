package com.javafx.habr_spring.repository.server;

import com.javafx.habr_spring.model.server.Push;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushRepository extends JpaRepository<Push, Long> {
}
