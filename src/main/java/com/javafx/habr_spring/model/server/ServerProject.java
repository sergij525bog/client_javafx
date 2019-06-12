package com.javafx.habr_spring.model.server;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class ServerProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ServerFile> files = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ServerUserProject> users = new HashSet<>();

    public ServerProject() {
    }

    public ServerProject(String name) {
        this.name = name;
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

    public Set<ServerFile> getFiles() {
        return files;
    }

    public void setFiles(Set<ServerFile> files) {
        this.files = files;
    }

    public Set<ServerUserProject> getUsers() {
        return users;
    }

    public void setUsers(Set<ServerUserProject> users) {
        this.users = users;
    }
}
