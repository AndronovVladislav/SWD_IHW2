package dependencies;

import utils.Color;

import java.util.List;

public interface TopologicalSortable {
    String sortingIsPossible();

    List<Vertex> topologicalSorting();

    default String sortingIsPossibleHelp(Vertex currentVertex,
                                         DependenciesGraph dependenciesGraph) {
        if (currentVertex.getColor() == Color.BLACK) {
            return "";
        } else if (currentVertex.getColor() == Color.GRAY) {
            return currentVertex.getFilename();
        } else {
            String incorrectFile = "";

            // one real vertex can have many copies in different edges,
            // so we recolor all copies of the real vertex in gray
            for (var edge : dependenciesGraph.getEdges()) {
                if (currentVertex.getFilename().equals(edge.getSource().getFilename())) {
                    edge.getSource().setColor(Color.GRAY);
                }

                if (currentVertex.getFilename().equals(edge.getDestination().getFilename())) {
                    edge.getDestination().setColor(Color.GRAY);
                }
            }

//            for (int i = 0; i < dependenciesGraph.getEdges().size(); ++i) {
//                if (currentVertex.getFilename().equals(dependenciesGraph.getEdges().get(i)
//                        .getSource().getFilename())) {
//                    dependenciesGraph.getEdges().get(i).getSource().setColor(Color.GRAY);
//                }
//
//                if (currentVertex.getFilename().equals(dependenciesGraph.getEdges().get(i)
//                        .getDestination().getFilename())) {
//                    dependenciesGraph.getEdges().get(i).getDestination().setColor(Color.GRAY);
//                }
//            }

            // try to find edges where current vertex is dependent file
            // i.e. current vertex is source of some edge(it is DFS-like algorithm)
            for (var edge : dependenciesGraph.getEdges()) {
                if (currentVertex.getFilename().equals(edge.getSource().getFilename())) {
                    edge.getSource().setColor(Color.GRAY);
                    incorrectFile = sortingIsPossibleHelp(edge.getDestination(), dependenciesGraph);

                    if (!incorrectFile.equals("")) {
                        return incorrectFile;
                    }
                }
            }

//            for (int i = 0; i < dependenciesGraph.getEdges().size(); ++i) {
//                if (currentVertex.getFilename().equals(dependenciesGraph.getEdges().get(i)
//                        .getSource().getFilename())) {
//                    dependenciesGraph.getEdges().get(i).getSource().setColor(Color.GRAY);
//                    incorrectFile = sortingIsPossibleHelp(dependenciesGraph.getEdges().get(i)
//                            .getDestination(), dependenciesGraph);
//
//                    if (!incorrectFile.equals("")) {
//                        return incorrectFile;
//                    }
//                }
//            }

            for (var edge : dependenciesGraph.getEdges()) {
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


//            for (int i = 0; i < dependenciesGraph.getEdges().size(); ++i) {
//                // Since one real vertex can have many copies in different edges we recolor edges
//                // where current vertex is source with black because we're sure that there are no
//                // cyclic dependencies in all subsequent vertices - else we won't reach this place.
//                if (currentVertex.getFilename().equals(dependenciesGraph.getEdges().get(i).
//                        getSource().getFilename())) {
//                    dependenciesGraph.getEdges().get(i).getSource().setColor(Color.BLACK);
//                }
//
//                // At the same time we recolor edges where current vertex is destination with white
//                // because real vertex might be in some dependency chains, which means we probably
//                // will return in her and her should no being gray vertex at this moment.
//                if (currentVertex.getFilename().equals(dependenciesGraph.getEdges().get(i)
//                        .getDestination().getFilename())) {
//                    dependenciesGraph.getEdges().get(i).getDestination().setColor(Color.WHITE);
//                }
//            }

            return incorrectFile;
        }
    }

    default void topologicalSortingHelp(Vertex currentVertex, List<Vertex> result,
                                        DependenciesGraph dependenciesGraph) {
        if (currentVertex.getColor() != Color.BLACK) {
            for (int i = 0; i < dependenciesGraph.getEdges().size(); i++) {
                if (currentVertex.getFilename().equals(dependenciesGraph.getEdges().get(i)
                        .getSource().getFilename())) {

                    dependenciesGraph.getEdges().get(i).getSource().setColor(Color.BLACK);
                    topologicalSortingHelp(dependenciesGraph.getEdges().get(i)
                            .getDestination(), result, dependenciesGraph);
                }
            }

            for (int i = 0; i < dependenciesGraph.getVertices().size(); i++) {
                if (dependenciesGraph.getVertices().get(i).getFilename().equals(currentVertex
                        .getFilename())) {

                    dependenciesGraph.getVertices().get(i).setColor(Color.BLACK);
                }
            }

            for (int i = 0; i < dependenciesGraph.getEdges().size(); i++) {
                if (dependenciesGraph.getEdges().get(i).getDestination().getFilename()
                        .equals(currentVertex.getFilename())) {

                    dependenciesGraph.getEdges().get(i).getDestination().setColor(Color.BLACK);
                }
            }

            result.add(currentVertex);
        }
    }
}
