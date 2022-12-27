package main;

import java.lang.ref.Reference;
import java.util.LinkedList;
import java.util.List;

public class DependenciesGraph {
    List<Vertex> vertices;
    List<Edge> edges;

    public DependenciesGraph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }

    void printGraph() {
        for (var vertex : vertices) {
            System.out.println(vertex.file);
        }

        for (var edge : edges) {
            System.out.println("(" + edge.source.file + ", " + edge.destination.file + ")");
        }
    }

    String topologicalSortingIsPossible() {
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
        if (currentVertex.color == Color.BLACK) {
            return "";
        } else if (currentVertex.color == Color.GRAY) {
            return currentVertex.file;
        } else {
            String ans = "";
            currentVertex.color = Color.GRAY;

            for (var edge : edges) {
                if (currentVertex.file.equals(edge.source.file)) {
                    ans = topologicalSortingIsPossibleHelp(edge.destination);
                }
            }

            currentVertex.color = Color.BLACK;
            return ans;
        }
    }

    void recolorInWhite() {
        for (var vertex : vertices) {
            vertex.color = Color.WHITE;
        }

        for (var edge : edges) {
            edge.source.color = Color.WHITE;
            edge.destination.color = Color.WHITE;
        }
    }

    List<Vertex> topologicalSorting() {
        List<Vertex> result = new LinkedList<>();

        for (var vertex : vertices) {
            topologicalSortingHelp(vertex, result);
        }

        return result;
    }

    void topologicalSortingHelp(Vertex currentVertex, List<Vertex> result) {
        if (currentVertex.color != Color.BLACK) {
            currentVertex.color = Color.GRAY;

            for (var edge : edges) {
                if (currentVertex.file.equals(edge.source.file)) {
                    edge.source.color = Color.BLACK;
                    topologicalSortingHelp(edge.destination, result);
                }
            }

            for (var vertex : vertices) {
                if (vertex.file.equals(currentVertex.file)) {
                    vertex.color = Color.BLACK;
                }
            }

            for (var edge : edges) {
                if (edge.destination.file.equals(currentVertex.file)) {
                    edge.destination.color = Color.BLACK;
                }
            }

            currentVertex.color = Color.BLACK;
            result.add(currentVertex);
        }
    }
}