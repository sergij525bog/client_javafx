package com.javafx.habr_spring.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files")
public class WriterFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "filename")
    private String filename;

    @Column(name = "filesize")
    private Long filesize;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommitData> commits = new HashSet<>();

    public WriterFile() {
    }

    public WriterFile(String filename, Long filesize) {
        this.filename = filename;
        this.filesize = filesize;
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
}
