package main;

import java.io.*;

public class FilesProcessor {
    static DependenciesGraph dependenciesGraph;
    final String rootDirectory;

    FilesProcessor(String mainDirectory) {
        dependenciesGraph = new DependenciesGraph();
        rootDirectory = mainDirectory;
    }

    boolean readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentString;

        dependenciesGraph.vertices.add(new Vertex(file.getAbsolutePath()));
        while ((currentString = reader.readLine()) != null) {
            int firstNonBlankSymbol = firstNonBlankSymbol(currentString);

            if ("require".equals(currentString.substring(firstNonBlankSymbol,
                                                         firstNonBlankSymbol + 7))) {
                String requiredFile = currentString.substring(firstNonBlankSymbol + 9, currentString.length() - 1);
                File checkRequestCorrectness = new File(rootDirectory + "\\" + requiredFile);

                if (!checkRequestCorrectness.isFile()) {
                    Errors.badRequire(checkRequestCorrectness, file);
                    return false;
                }

                dependenciesGraph.edges.add(new Edge(new Vertex(file.getAbsolutePath()),
                                                     new Vertex(checkRequestCorrectness.getAbsolutePath())));
            }
        }
        return true;
    }
    int firstNonBlankSymbol(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != '\t' && string.charAt(i) != ' ') {
                return i;
            }
        }
        return string.length() - 1;
    }

    void printFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentString;

        while ((currentString = reader.readLine()) != null) {
            System.out.println(currentString);
        }
    }
}
