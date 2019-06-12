package com.javafx.habr_spring.model.server;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
public class ServerUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "activationCode")
    private String activationCode;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ServerUserProject> projects = new HashSet<>();

    public ServerUser() {
    }

    public ServerUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public ServerUser(String username, String password, String email, boolean isActive, String activationCode) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.activationCode = activationCode;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Set<ServerUserProject> getProjects() {
        return projects;
    }

    public Set<ServerRoles> getRoles() {
        Set<ServerUserProject> projects = getProjects();
        Set<ServerRoles> roles = new HashSet<>();
        for(ServerUserProject project : projects) {
            roles.addAll(Collections.singleton(project.getRoles()));
        }
        return roles;
    }

    public void setProjects(Set<ServerUserProject> projects) {
        this.projects = projects;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
