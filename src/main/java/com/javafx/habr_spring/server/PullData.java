package com.javafx.habr_spring.server;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commit_data")
public class PullData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "filedata")
    private byte[] filedata;

    @Column(name = "filesize")
    private Long filesize;

    @NotNull
    @Column(name = "commit_date")
    private Date commit_date;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private ServerFile commit;

    public PullData() {
    }

    public PullData(byte[] filedata, Long filesize, Date commit_date, String description, ServerFile commit) {
        this.filedata = filedata;
        this.filesize = filesize;
        this.commit_date = commit_date;
        this.description = description;
        this.commit = commit;
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

    public Date getCommit_date() {
        return commit_date;
    }

    public void setCommit_date(Date commit_date) {
        this.commit_date = commit_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServerFile getCommit() {
        return commit;
    }

    public void setCommit(ServerFile commit) {
        this.commit = commit;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}
