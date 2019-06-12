package com.javafx.habr_spring.model.client;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class ClientProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ClientFile> files = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    Set<ClientUser> users = new HashSet<>();

    public ClientProject() {
    }

    public ClientProject(String name) {
        this.name = name;
    }

    public ClientProject(String name, Set<ClientFile> files) {
        this.name = name;
        this.files = files;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ClientFile> getFiles() {
        return files;
    }

    public void setFiles(Set<ClientFile> files) {
        this.files = files;
    }

    public Set<ClientUser> getUsers() {
        return users;
    }

    public void setUsers(Set<ClientUser> users) {
        this.users = users;
    }
}
