package com.javafx.habr_spring.client.repository;

import com.javafx.habr_spring.client.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
