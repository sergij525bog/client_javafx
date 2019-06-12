package com.javafx.habr_spring.model.server;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "push_data")
public class Push {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "filedata", columnDefinition = "OID")
    private byte[] filedata;

    @Column(name = "filesize")
    private Long filesize;

    @Column(name = "pushDate")
    private Date pushDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private ServerFile currentFile;

    public Push() {
    }

    public Push(byte[] filedata, Long filesize, Date pushDate, String description, ServerFile currentFile) {
        this.filedata = filedata;
        this.filesize = filesize;
        this.pushDate = pushDate;
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

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServerFile getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(ServerFile currentFile) {
        this.currentFile = currentFile;
    }
}
