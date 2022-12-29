package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    public static void printFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentString;

        while ((currentString = reader.readLine()) != null) {
            System.out.println(currentString);
        }
    }
}
