package utils;

import java.io.File;

public class Errors {
    public static void cyclicDependency(String problemFile) {
        System.out.println("Cyclic dependency detected! Problem file: " + problemFile);
    }

    public static void badRequire(File require, File file) {
        if (require.isDirectory()) {
            System.out.println("Require-statement contains just directory name \"" +
                                require.getName() + "\" in file " + file.getAbsolutePath());
        } else {
            System.out.println("Require-statement contains non-existent filename \"" +
                                require.getName() + "\" in file " + file.getAbsolutePath());
        }
    }

    public static void badStartDirectory() {
        System.out.println("Start path is invalid!");
    }
}
