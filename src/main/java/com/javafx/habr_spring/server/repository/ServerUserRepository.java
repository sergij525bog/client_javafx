package com.javafx.habr_spring.server.repository;

import com.javafx.habr_spring.server.domain.ServerUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerUserRepository extends CrudRepository<ServerUser, Long> {
}
