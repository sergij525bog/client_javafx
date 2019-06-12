package com.javafx.habr_spring.model.client;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commit_data")
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "filedata", columnDefinition = "OID")
    private byte[] filedata;

    @Column(name = "filesize")
    private Long filesize;

    @Column(name = "commitDate")
    private Date commitDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private ClientFile currentFile;

    public Commit() {
    }

    public Commit(byte[] filedata, Long filesize, Date commitDate, String description, ClientFile currentFile) {
        this.filedata = filedata;
        this.filesize = filesize;
        this.commitDate = commitDate;
        this.description = description;
        this.currentFile = currentFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientFile getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(ClientFile currentFile) {
        this.currentFile = currentFile;
    }
}
