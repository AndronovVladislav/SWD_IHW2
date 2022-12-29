package data_processing;

import dependencies.DependenciesGraph;
import dependencies.Edge;
import dependencies.Vertex;
import utils.Errors;

import java.io.*;

public class FilesProcessor {
    private final String rootDirectory;

    public FilesProcessor(String mainDirectory) {
        rootDirectory = mainDirectory;
    }

    /**
     * The method that processes a single file - reads the file,
     * trying to find require-statement:<br></br>
     * 1. First of all, adds vertex containing filename in the <code>vertices</code> of the
     * <code>dependenciesGraph</code>. It is necessary for non-single connectivity components
     * directory configurations<br></br>
     * 2. If a dependency found, the method adds the dependent pair to the <code>edges</code> of
     * the <code>dependenciesGraph</code><br></br>
     *
     * @param file file to processing
     * @param dependenciesGraph dependencies graph to add dependencies to him
     * @throws IOException <code>reader.readLine()</code> can throw this exception
     */
    public boolean findDependencies(File file, DependenciesGraph dependenciesGraph) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentString;
        int firstVisibleSymbolPos;

        Vertex fileVertex = new Vertex(file.getAbsolutePath());
        dependenciesGraph.getVertices().add(fileVertex);

        while ((currentString = reader.readLine()) != null) {
            // this variable(and corresponding method) necessary for the most flexible search of a require-statement
            firstVisibleSymbolPos = firstNonBlankSymbol(currentString);

            // 7 - length of the word "require"
            if ("require".equals(currentString.substring(firstVisibleSymbolPos, firstVisibleSymbolPos + 7))) {
                // extract name of required file and try to open it
                String requiredFile = currentString.substring(firstVisibleSymbolPos + 9, currentString.length() - 1);
                File checkRequestCorrectness = new File(rootDirectory + "\\" + requiredFile);

                if (!checkRequestCorrectness.isFile()) {
                    Errors.badRequire(checkRequestCorrectness, file);
                    return false;
                }

                // if file exists add dependent pair in dependenciesGraph
                dependenciesGraph.getEdges()
                                 .add(new Edge(fileVertex,
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
}
