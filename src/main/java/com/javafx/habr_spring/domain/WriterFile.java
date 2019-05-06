package com.javafx.habr_spring.domain;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files")
public class WriterFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@NotNull
    @Column(name = "filename")
    private Path filename;*/

    @NotNull
    @Column(name = "filedata")
    private byte[] filedata;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommitData> commits = new HashSet<>();

    public WriterFile() {
    }

    /*public WriterFile(Path filename, byte[] filedata, Project project, Set<CommitData> commits) {
        this.filename = filename;
        this.filedata = filedata;
        this.project = project;
        this.commits = commits;
    }*/

    public WriterFile(byte[] filedata, Project project, Set<CommitData> commits) {
        this.filedata = filedata;
        this.project = project;
        this.commits = commits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Path getFilename() {
        return filename;
    }

    public void setFilename(Path filename) {
        this.filename = filename;
    }*/

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<CommitData> getCommits() {
        return commits;
    }

    public void setCommits(Set<CommitData> commits) {
        this.commits = commits;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}
