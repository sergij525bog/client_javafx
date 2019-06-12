package com.javafx.habr_spring.repository.server;

import com.javafx.habr_spring.model.server.ServerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerUserRepository extends JpaRepository<ServerUser, Long> {
    ServerUser findByUsername(String username);
    ServerUser findByActivationCode(String activationCode);
}
