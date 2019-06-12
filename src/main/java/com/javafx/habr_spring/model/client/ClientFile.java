package com.javafx.habr_spring.model.client;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files")
public class ClientFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filesize")
    private Long filesize;

    @Lob
    @Column(name = "filedata", columnDefinition = "OID")
    private byte[] filedata;

    @OneToMany(mappedBy = "currentFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Commit> commits = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private ClientProject project;

    public ClientFile() {
    }

    public ClientFile(String filename, Long filesize, byte[] filedata, ClientProject project) {
        this.filename = filename;
        this.filesize = filesize;
        this.filedata = filedata;
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

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public Set<Commit> getCommits() {
        return commits;
    }

    public void setCommits(Set<Commit> commits) {
        this.commits = commits;
    }

    public ClientProject getProject() {
        return project;
    }

    public void setProject(ClientProject project) {
        this.project = project;
    }
}
