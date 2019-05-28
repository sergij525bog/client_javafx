package com.javafx.habr_spring.client.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
public class ClientUser {
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

    @ElementCollection(targetClass = ClientRoles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<ClientRoles> roles;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_projects",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<ClientProject> clientProjects = new HashSet<>();

    public ClientUser() {
    }

    public ClientUser(String username, String password, boolean active, Set<ClientRoles> roles, Set<ClientProject> clientProjects) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.clientProjects = clientProjects;
    }

    public ClientUser(String username, String password, boolean active, String activationCode, String email, Set<ClientRoles> roles, Set<ClientProject> clientProjects) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.activationCode = activationCode;
        this.email = email;
        this.roles = roles;
        this.clientProjects = clientProjects;
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

    public Set<ClientRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<ClientRoles> roles) {
        this.roles = roles;
    }

    public Set<ClientProject> getClientProjects() {
        return clientProjects;
    }

    public void setClientProjects(Set<ClientProject> clientProjects) {
        this.clientProjects = clientProjects;
    }
}
