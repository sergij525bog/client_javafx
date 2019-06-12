package com.javafx.habr_spring.model.server;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files")
public class ServerFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filesize")
    private Long filesize;

    @Lob
    @Column(name = "filedata", columnDefinition = "OID")
    private byte[] filedata;

    @OneToMany(mappedBy = "currentFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Push> pushData = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private ServerProject project;

    public ServerFile() {
    }

    public ServerFile(String filename, Long filesize, byte[] filedata) {
        this.filename = filename;
        this.filesize = filesize;
        this.filedata = filedata;
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

    public Set<Push> getPushData() {
        return pushData;
    }

    public void setPushData(Set<Push> pushData) {
        this.pushData = pushData;
    }

    public ServerProject getProject() {
        return project;
    }

    public void setProject(ServerProject project) {
        this.project = project;
    }
}
