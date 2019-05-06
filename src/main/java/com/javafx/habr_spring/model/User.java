package com.javafx.habr_spring.model;

import com.sun.istack.internal.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(name = "username")
    private String uername;

    @NotNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "active")
    private boolean active;

    @Column(name = "activationCode")
    private String activationCode;

    @Column(name = "email")
    private String email;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_projects",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projects = new HashSet<>();

    public User() {
    }

    public User(String uername, String password, boolean active, Set<Roles> roles, Set<Project> projects) {
        this.uername = uername;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.projects = projects;
    }

    public User(String uername, String password, boolean active, String activationCode, String email, Set<Roles> roles, Set<Project> projects) {
        this.uername = uername;
        this.password = password;
        this.active = active;
        this.activationCode = activationCode;
        this.email = email;
        this.roles = roles;
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUername() {
        return uername;
    }

    public void setUername(String uername) {
        this.uername = uername;
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

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
