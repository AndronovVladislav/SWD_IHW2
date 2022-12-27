package data_processing;

import dependencies.DependenciesGraph;
import dependencies.Edge;
import dependencies.Vertex;
import utils.Errors;

import java.io.*;

public class FilesProcessor {
    final String rootDirectory;

    public FilesProcessor(String mainDirectory) {
        rootDirectory = mainDirectory;
    }

    public boolean processFile(File file, DependenciesGraph dependenciesGraph) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentString;
        int firstVisibleSymbolPos;

        dependenciesGraph.getVertices().add(new Vertex(file.getAbsolutePath()));

        while ((currentString = reader.readLine()) != null) {
            firstVisibleSymbolPos = firstNonBlankSymbol(currentString);

            if ("require".equals(currentString.substring(firstVisibleSymbolPos, firstVisibleSymbolPos + 7))) {

                String requiredFile = currentString.substring(firstVisibleSymbolPos + 9, currentString.length() - 1);
                File checkRequestCorrectness = new File(rootDirectory + "\\" + requiredFile);

                if (!checkRequestCorrectness.isFile()) {
                    Errors.badRequire(checkRequestCorrectness, file);
                    return false;
                }

                dependenciesGraph.getEdges().add(new Edge(new Vertex(file.getAbsolutePath()),
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

    public void printFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentString;

        while ((currentString = reader.readLine()) != null) {
            System.out.println(currentString);
        }
    }
}
