package utils;

import dependencies.Vertex;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

abstract public class Utilities {
    public static final Scanner scanner = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static boolean checkIsDirectory(File potentialDirectory) {
        return Objects.nonNull(potentialDirectory) && potentialDirectory.isDirectory();
    }

    public static boolean makeResultFile(String rootFolder) throws IOException {
        File resultFile = new File(rootFolder + "\\result_file.result");
        return resultFile.createNewFile();
    }

    public static void fillResultFile(File file, List<Vertex> correctDependenciesList) throws IOException {
        try (FileWriter resultFile = new FileWriter(file, false)) {
            for (var vertex : correctDependenciesList) {
                printFile(resultFile, new File(vertex.getFilename()));
            }
        }
    }

    static void printFile(FileWriter resultFile, File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentString;

        while ((currentString = reader.readLine()) != null) {
            resultFile.write(currentString + "\n");
        }
    }

    public static boolean resultFileNotExist(String rootFolder) {
        File checkFile = new File(rootFolder + "\\result_file.result");
        return !checkFile.isFile();
    }
}
