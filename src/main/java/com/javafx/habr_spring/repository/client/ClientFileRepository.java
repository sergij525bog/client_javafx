package com.javafx.habr_spring.repository.client;

import com.javafx.habr_spring.model.client.ClientFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
public interface ClientFileRepository extends JpaRepository<ClientFile, Long> {
    ClientFile findByFilename(String filename);
    ArrayList<ClientFile> findByProject(Long id);
}