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
        String sortingIsPossible = "";
        for (var vertex : vertices) {
            sortingIsPossible = topologicalSortingIsPossibleHelp(vertex);
            if (!sortingIsPossible.equals("")) {
                break;
            }
        }
        recolorInWhite();
        return sortingIsPossible;
    }

    String topologicalSortingIsPossibleHelp(Vertex currentVertex) {
        if (currentVertex.getColor() == Color.BLACK) {
            return "";
        } else if (currentVertex.getColor() == Color.GRAY) {
            return currentVertex.getFilename();
        } else {
            String ans = "";
            currentVertex.setColor(Color.GRAY);

            for (var edge : edges) {
                if (currentVertex.getFilename().equals(edge.source.getFilename())) {
                    ans = topologicalSortingIsPossibleHelp(edge.destination);
                }
            }

            currentVertex.setColor(Color.BLACK);
            return ans;
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