package data_processing;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class Database {
    Set<File> directories;
    Set<File> files;
    Set<File> usedDirectories;

    public Database() {
        directories = new TreeSet<>();
        files = new TreeSet<>();
        usedDirectories = new TreeSet<>();
    }

    public Set<File> getDirectories() {
        return directories;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void fillDatabase() {
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
