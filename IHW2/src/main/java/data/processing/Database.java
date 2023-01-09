package data.processing;

import java.io.File;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Database {
    private final Set<File> directories;
    private final Set<File> files;
    private final Set<File> usedDirectories;

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

    public void addDirectories(File newDirectory) {
        directories.add(newDirectory);
    }

    public void fillDatabase() {
        while (usedDirectories.size() < directories.size()) {
            TreeSet<File> directories_copy = new TreeSet<>(directories);

            for (var directory :  directories_copy) {
                if (usedDirectories.contains(directory)) {
                    continue;
                }

                for (var directoryItem : Objects.requireNonNull(directory.listFiles())) {
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
