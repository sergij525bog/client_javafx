package com.javafx.habr_spring.server;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files")
public class ServerFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "filename")
    private String filename;

    @NotNull
    @Lob
    @Column(name = "filedata", columnDefinition="OID")
    private byte[] filedata;

    @NotNull
    @Column(name = "filesize")
    private Long filesize;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PullData> commits = new HashSet<>();

    public ServerFile() {
    }

    public ServerFile(String filename, byte[] filedata, Long filesize, Project project) {
        this.filename = filename;
        this.filedata = filedata;
        this.filesize = filesize;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<PullData> getCommits() {
        return commits;
    }

    public void setCommits(Set<PullData> commits) {
        this.commits = commits;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}
