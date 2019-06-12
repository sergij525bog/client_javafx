package com.javafx.habr_spring.repository.client;

import com.javafx.habr_spring.model.client.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {
}
