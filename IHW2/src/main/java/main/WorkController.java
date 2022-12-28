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

    WorkController(String startDirectory) {
        mainFolder = startDirectory;

        database = new Database();
        database.getDirectories().add(new File(mainFolder));
        filesProcessor = new FilesProcessor(mainFolder);
        dependenciesGraph = new DependenciesGraph();
    }

    void workLoop() throws IOException {
        database.fillDatabase();

        for (var file : database.getFiles()) {
            if (!filesProcessor.processFile(file, dependenciesGraph)) {
                return;
            }
        }

        // filesProcessor.dependenciesGraph.printGraph();
        processDependencies();
    }

    void processDependencies() throws IOException {
        String incorrectFile = dependenciesGraph.topologicalSortingIsPossible();

        if (incorrectFile.equals("")) {
            processCorrectRequest();
        } else {
            Errors.cyclicDependency(incorrectFile);
        }
    }

    boolean checkBeforeStart() {
        return Utilities.checkIsDirectory(database.getDirectories().iterator().next());
    }

    void processCorrectRequest() throws IOException {
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
            filesProcessor.printFile(new File(file.getFilename()));
        }
    }
}

