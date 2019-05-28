package com.javafx.habr_spring.server.domain;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class ServerProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<ServerUser> serverUsers = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ServerFile> files = new HashSet<>();

    public ServerProject() {
    }

    public ServerProject(String name, Set<ServerUser> serverUsers) {
        this.name = name;
        this.serverUsers = serverUsers;
    }

    public ServerProject(String name, Set<ServerUser> serverUsers, Set<ServerFile> files) {
        this.name = name;
        this.serverUsers = serverUsers;
        this.files = files;
    }
}
