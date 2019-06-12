package com.javafx.habr_spring.model.server;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_project_roles")
public class ServerUserProject {

    @EmbeddedId
    private ServerUserProjectId id;

    @ManyToOne
    @JoinColumn(name = "user_fk", insertable = false, updatable = false)
    private ServerUser user;

    @ManyToOne
    @JoinColumn(name = "project_fk", insertable = false, updatable = false)
    private ServerProject project;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ServerRoles roles;

    public ServerUserProject() {
    }

    public ServerUserProject(ServerUser user, ServerProject project, ServerRoles roles) {
        this.id = new ServerUserProjectId(user.getId(), project.getId());
        this.user = user;
        this.project = project;
        this.roles = roles;
    }

    public ServerUserProjectId getId() {
        return id;
    }

    public void setId(ServerUserProjectId id) {
        this.id = id;
    }

    public ServerUser getUser() {
        return user;
    }

    public void setUser(ServerUser user) {
        this.user = user;
    }

    public ServerProject getProject() {
        return project;
    }

    public void setProject(ServerProject project) {
        this.project = project;
    }

    public ServerRoles getRoles() {
        return roles;
    }

    public void setRoles(ServerRoles roles) {
        this.roles = roles;
    }

    @Embeddable
    public static class ServerUserProjectId implements Serializable {
        @Column(name = "user_fk")
        private Long userId;

        @Column(name = "project_fk")
        private Long projectId;

        public ServerUserProjectId() {
        }

        public ServerUserProjectId(Long userId, Long projectId) {
            this.userId = userId;
            this.projectId = projectId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;

            ServerUserProjectId other = (ServerUserProjectId) obj;

            if (userId == null) {
                if (other.userId != null)
                    return false;
            } else if (!userId.equals(other.userId))
                return false;

            if (projectId == null) {
                if (other.projectId != null)
                    return false;
            } else if (!projectId.equals(other.projectId))
                return false;

            return true;

        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((userId == null) ? 0 : userId.hashCode());
            result = prime * result
                    + ((projectId == null) ? 0 : projectId.hashCode());
            return result;
        }
    }
}
