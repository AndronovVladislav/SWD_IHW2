package utils;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Utilities {
    public static Scanner scanner = new Scanner(System.in);

    public static boolean checkIsDirectory(File potentialDirectory) {
        return Objects.nonNull(potentialDirectory) && potentialDirectory.isDirectory();
    }
}
