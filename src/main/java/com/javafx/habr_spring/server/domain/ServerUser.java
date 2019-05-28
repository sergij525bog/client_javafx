package com.javafx.habr_spring.server.domain;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
public class ServerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "activationCode")
    private String activationCode;

    @Column(name = "email")
    private String email;

    @ElementCollection(targetClass = ServerRoles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<ServerRoles> roles;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_projects",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<ServerProject> serverProjects = new HashSet<>();

    public ServerUser() {
    }

    public ServerUser(String username, String password, boolean active, Set<ServerRoles> roles, Set<ServerProject> serverProjects) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.serverProjects = serverProjects;
    }

    public ServerUser(String username, String password, boolean active, String activationCode, String email, Set<ServerRoles> roles, Set<ServerProject> serverProjects) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.activationCode = activationCode;
        this.email = email;
        this.roles = roles;
        this.serverProjects = serverProjects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ServerRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<ServerRoles> roles) {
        this.roles = roles;
    }

    public Set<ServerProject> getServerProjects() {
        return serverProjects;
    }

    public void setServerProjects(Set<ServerProject> serverProjects) {
        this.serverProjects = serverProjects;
    }
}
