package main;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Database {
    static Set<File> directories;
    static Set<File> files;
    static Set<File> usedFiles;
    static Set<File> usedDirectories;

    Database() {
        directories = new TreeSet<>();
        files = new TreeSet<>();
        usedFiles = new TreeSet<>();
        usedDirectories = new TreeSet<>();
    }

    static void fillDatabase() {
        while (usedDirectories.size() != directories.size()) {
            for (var directory : directories) {
                for (var directoryItem : directory.listFiles()) {
                    if (directoryItem.isDirectory()) {
                        directories.add(directoryItem);
                    } else {
                        files.add(directoryItem);
                    }
                }

                usedDirectories.add(directory);
            }
        }
    }
}
