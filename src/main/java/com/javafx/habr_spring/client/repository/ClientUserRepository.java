package com.javafx.habr_spring.client.repository;

import com.javafx.habr_spring.client.domain.ClientUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientUserRepository extends CrudRepository<ClientUser, Long> {
}
