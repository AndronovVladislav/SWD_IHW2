package main;

import java.io.File;

public class Errors {
    static void cyclicDependency(String problemFile) {
        System.out.println("Cyclic dependency detected! Problem file:" + problemFile);
    }

    static void badRequire(File require, File file) {
        if (require.isDirectory()) {
            System.out.println("Require-statement contains just directory name \"" +
                                require.getName() + "\" in file " + file.getAbsolutePath());
        } else {
            System.out.println("Require-statement contains non-existent file name \"" +
                                require.getName() + "\" in file " + file.getAbsolutePath());
        }
    }

    static void badStartDirectory() {
        System.out.println("Start path is invalid!");
    }
}
