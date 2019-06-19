package com.javafx.habr_spring.service;

import javafx.scene.control.TreeItem;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class FileTreeItem extends CheckBoxTreeItem<Path> {
    private boolean isLeaf;

    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;

    public FileTreeItem(Path path) {
        super(path);
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {

            isFirstTimeLeaf = false;
            Path path = getValue();
            isLeaf = Files.isRegularFile(path);
        }

        return isLeaf;
    }

    @Override
    public ObservableList<TreeItem<Path>> getChildren() {

        if (isFirstTimeChildren) {

            isFirstTimeChildren = false;

            // First getChildren() call, so we actually go off and
            // determine the children of the file contained in this TreeItem.
            super.getChildren().setAll(buildChildren(this));
        }

        return super.getChildren();
    }

    private ObservableList<TreeItem<Path>> buildChildren(
            CheckBoxTreeItem<Path> treeItem) {

        Path path = treeItem.getValue();

        if ((path != null) && (Files.isDirectory(path))) {

            try(Stream<Path> pathStream = Files.list(path)) {

                return pathStream
                        .map(p -> new FileTreeItem(p))
                        .collect(Collectors.toCollection(() ->
                                FXCollections.observableArrayList()));
            }
            catch(IOException e) {

                throw new UncheckedIOException(e);
            }
        }

        return FXCollections.emptyObservableList();
    }
}
