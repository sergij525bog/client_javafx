package com.javafx.habr_spring.domain;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WriterFile> files = new HashSet<>();

    public Project() {
    }

    public Project(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Project(String name, Set<User> users, Set<WriterFile> files) {
        this.name = name;
        this.users = users;
        this.files = files;
    }
}
