package dependencies;

import utils.Color;

import java.util.LinkedList;
import java.util.List;

public class DependenciesGraph {
    final private List<Vertex> vertices;
    final private List<Edge> edges;

    public DependenciesGraph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    void printGraph() {
        for (var vertex : vertices) {
            System.out.println(vertex.getFilename());
        }

        for (var edge : edges) {
            System.out.println("(" + edge.source.getFilename() + ", " +
                                     edge.destination.getFilename() + ")");
        }
    }

    public String topologicalSortingIsPossible() {
        String incorrectFile = "";

        for (int i = 0; i < edges.size(); ++i) {
            incorrectFile = topologicalSortingIsPossibleHelp(edges.get(i).source);
            if (!incorrectFile.equals("")) {
                break;
            }
        }

        recolorInWhite();
        return incorrectFile;
    }

    String topologicalSortingIsPossibleHelp(Vertex currentVertex) {
        if (currentVertex.getColor() == Color.BLACK) {
            return "";
        } else if (currentVertex.getColor() == Color.GRAY) {
            return currentVertex.getFilename();
        } else {
            String incorrectFile = "";

            for (int i = 0; i < edges.size(); ++i) {
                if (currentVertex.getFilename().equals(edges.get(i).source.getFilename())) {
                    edges.get(i).source.setColor(Color.GRAY);
                }

                if (currentVertex.getFilename().equals(edges.get(i).destination.getFilename())) {
                    edges.get(i).destination.setColor(Color.GRAY);
                }
            }

            for (int i = 0; i < edges.size(); ++i) {
                if (currentVertex.getFilename().equals(edges.get(i).source.getFilename())) {
                    edges.get(i).source.setColor(Color.GRAY);
                    incorrectFile = topologicalSortingIsPossibleHelp(edges.get(i).destination);

                    if (!incorrectFile.equals("")) {
                        return incorrectFile;
                    }
                }
            }

            for (int i = 0; i < edges.size(); ++i) {
                if (currentVertex.getFilename().equals(edges.get(i).source.getFilename())) {
                    edges.get(i).source.setColor(Color.BLACK);
                }

                if (currentVertex.getFilename().equals(edges.get(i).destination.getFilename())) {
                    edges.get(i).destination.setColor(Color.WHITE);
                }
            }

            return incorrectFile;
        }
    }

    void recolorInWhite() {
        for (var vertex : vertices) {
            vertex.setColor(Color.WHITE);
        }

        for (var edge : edges) {
            edge.source.setColor(Color.WHITE);
            edge.destination.setColor(Color.WHITE);
        }
    }

    public List<Vertex> topologicalSorting() {
        List<Vertex> result = new LinkedList<>();

        for (var vertex : vertices) {
            topologicalSortingHelp(vertex, result);
        }

        return result;
    }

    void topologicalSortingHelp(Vertex currentVertex, List<Vertex> result) {
        if (currentVertex.getColor() != Color.BLACK) {
            currentVertex.setColor(Color.GRAY);

            for (var edge : edges) {
                if (currentVertex.getFilename().equals(edge.source.getFilename())) {
                    edge.source.setColor(Color.BLACK);
                    topologicalSortingHelp(edge.destination, result);
                }
            }

            for (var vertex : vertices) {
                if (vertex.getFilename().equals(currentVertex.getFilename())) {
                    vertex.setColor(Color.BLACK);
                }
            }

            for (var edge : edges) {
                if (edge.destination.getFilename().equals(currentVertex.getFilename())) {
                    edge.destination.setColor(Color.BLACK);
                }
            }

            currentVertex.setColor(Color.BLACK);
            result.add(currentVertex);
        }
    }
}