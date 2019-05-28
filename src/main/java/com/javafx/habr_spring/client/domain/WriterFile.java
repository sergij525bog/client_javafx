package com.javafx.habr_spring.client.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files")
public class WriterFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Lob
    @Column(name = "filedata", columnDefinition="OID")
    private byte[] filedata;

    @Column(name = "filesize")
    private Long filesize;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private ClientProject clientProject;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommitData> commits = new HashSet<>();

    public WriterFile() {
    }

    public WriterFile(String filename, byte[] filedata, Long filesize, ClientProject clientProject) {
        this.filename = filename;
        this.filedata = filedata;
        this.filesize = filesize;
        this.clientProject = clientProject;
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

    public ClientProject getClientProject() {
        return clientProject;
    }

    public void setClientProject(ClientProject clientProject) {
        this.clientProject = clientProject;
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

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}
