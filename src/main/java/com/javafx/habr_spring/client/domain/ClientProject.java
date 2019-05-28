package com.javafx.habr_spring.client.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class ClientProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<ClientUser> clientUsers = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WriterFile> files = new HashSet<>();

    public ClientProject() {
    }

    public ClientProject(String name, Set<ClientUser> clientUsers) {
        this.name = name;
        this.clientUsers = clientUsers;
    }

    public ClientProject(String name, Set<ClientUser> clientUsers, Set<WriterFile> files) {
        this.name = name;
        this.clientUsers = clientUsers;
        this.files = files;
    }
}
