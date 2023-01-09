package dependencies;

import utils.Color;

import java.util.List;

public class SortingHelper {
    final private DependenciesGraph sortingGraph;

    SortingHelper(DependenciesGraph newGraph) {
        sortingGraph = newGraph;
    }

    String sortingIsPossibleHelp(Vertex currentVertex) {
        if (currentVertex.getColor() == Color.BLACK) {
            return "";
        } else if (currentVertex.getColor() == Color.GRAY) {
            return currentVertex.getFilename();
        } else {
            String incorrectFile = "";

            // one real vertex can have many copies in different edges,
            // so we recolor all copies of the real vertex in gray
            for (var edge : sortingGraph.getEdges()) {
                if (currentVertex.getFilename().equals(edge.getSource().getFilename())) {
                    edge.getSource().setColor(Color.GRAY);
                }

                if (currentVertex.getFilename().equals(edge.getDestination().getFilename())) {
                    edge.getDestination().setColor(Color.GRAY);
                }
            }

            // try to find edges where current vertex is dependent file
            // i.e. current vertex is source of some edge(it is DFS-like algorithm)
            for (var edge : sortingGraph.getEdges()) {
                if (currentVertex.getFilename().equals(edge.getSource().getFilename())) {
                    edge.getSource().setColor(Color.GRAY);
                    incorrectFile = sortingIsPossibleHelp(edge.getDestination());

                    if (!incorrectFile.equals("")) {
                        return incorrectFile;
                    }
                }
            }

            for (var edge : sortingGraph.getEdges()) {
                // Since one real vertex can have many copies in different edges we recolor edges
                // where current vertex is source with black because we're sure that there are no
                // cyclic dependencies in all subsequent vertices - else we won't reach this place.
                if (currentVertex.getFilename().equals(edge.getSource().getFilename())) {
                    edge.getSource().setColor(Color.BLACK);
                }

                // At the same time we recolor edges where current vertex is destination with white
                // because real vertex might be in some dependency chains, which means we probably
                // will return in her and her should no being gray vertex at this moment.
                if (currentVertex.getFilename().equals(edge.getDestination().getFilename())) {
                    edge.getDestination().setColor(Color.WHITE);
                }
            }

            return incorrectFile;
        }
    }

    void topologicalSortingHelp(Vertex currentVertex, List<Vertex> result) {
        if (currentVertex.getColor() != Color.BLACK) {
            for (var edge : sortingGraph.getEdges()) {
                if (currentVertex.getFilename().equals(edge.getSource().getFilename())) {
                    edge.getSource().setColor(Color.BLACK);
                    topologicalSortingHelp(edge.getDestination(), result);
                }
            }

            for (var vertex : sortingGraph.getVertices()) {
                if (vertex.getFilename().equals(currentVertex.getFilename())) {
                    vertex.setColor(Color.BLACK);
                }
            }

            for (var edge : sortingGraph.getEdges()) {
                if (edge.getDestination().getFilename().equals(currentVertex.getFilename())) {
                    edge.getDestination().setColor(Color.BLACK);
                }
            }

            result.add(currentVertex);
        }
    }
}
