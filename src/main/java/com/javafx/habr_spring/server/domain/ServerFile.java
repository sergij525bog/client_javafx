package com.javafx.habr_spring.server.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files")
public class ServerFile {
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
    private ServerProject serverProject;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PushData> commits = new HashSet<>();

    public ServerFile() {
    }

    public ServerFile(String filename, byte[] filedata, Long filesize, ServerProject serverProject) {
        this.filename = filename;
        this.filedata = filedata;
        this.filesize = filesize;
        this.serverProject = serverProject;
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

    public ServerProject getServerProject() {
        return serverProject;
    }

    public void setServerProject(ServerProject serverProject) {
        this.serverProject = serverProject;
    }

    public Set<PushData> getCommits() {
        return commits;
    }

    public void setCommits(Set<PushData> commits) {
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
