package main;

import data_processing.Database;
import data_processing.FilesProcessor;
import dependencies.DependenciesGraph;
import dependencies.Vertex;
import utils.Errors;
import utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WorkController {
    final String mainFolder;
    final FilesProcessor filesProcessor;
    final Database database;
    final DependenciesGraph dependenciesGraph;

    public WorkController(String startDirectory) {
        mainFolder = startDirectory;

        database = new Database();
        database.addDirectories(new File(mainFolder));
        filesProcessor = new FilesProcessor(mainFolder);
        dependenciesGraph = new DependenciesGraph();
    }

    /**
     * The main method in the program:<br></br>
     * 1. Scans all directories in the root directory<br></br>
     * 2. Processes all found files: reads them and finds dependencies in their content<br></br>
     * 3. Runs the function that processes the found dependencies
     * @throws IOException <code>filesProcessor.findDependencies()</code> can throw this exception
     */
    public void workLoop() throws IOException {
        database.fillDatabase();

        for (var file : database.getFiles()) {
            if (!filesProcessor.findDependencies(file, dependenciesGraph)) {
                return;
            }
        }

        processDependencies();
    }

    private void processDependencies() throws IOException {
        String incorrectFile = dependenciesGraph.sortingIsPossible();

        if (incorrectFile.equals("")) {
            processCorrectRequest();
        } else {
            Errors.cyclicDependency(incorrectFile);
        }
    }

    /**
     * The method that test the root directory for correctness
     * @return boolean
     */
    boolean checkBeforeStart() {
        return Utilities.checkIsDirectory(database.getDirectories().iterator().next());
    }

    private void processCorrectRequest() throws IOException {
        List<Vertex> correctDependenciesList = dependenciesGraph.topologicalSorting();

        System.out.println(Utilities.ANSI_GREEN + "Sorted list of files:" + Utilities.ANSI_RESET);
        for (var file : correctDependenciesList) {
            boolean independentFile = true;

            for (var edge : dependenciesGraph.getEdges()) {
                independentFile &= !edge.getSource().getFilename().equals(file.getFilename());
            }

            if (independentFile) {
                System.out.println(Utilities.ANSI_BLUE +
                        file.getFilename().substring(mainFolder.length() + 1) +
                        Utilities.ANSI_RESET);
            } else {
                System.out.println(Utilities.ANSI_YELLOW +
                        file.getFilename().substring(mainFolder.length() + 1) +
                        Utilities.ANSI_RESET);
            }
        }

        System.out.println("\n" + Utilities.ANSI_GREEN +
                           "Contents of files according to sorting:" + Utilities.ANSI_RESET);

        for (var file : correctDependenciesList) {
            Utilities.printFile(new File(file.getFilename()));
        }
    }
}

