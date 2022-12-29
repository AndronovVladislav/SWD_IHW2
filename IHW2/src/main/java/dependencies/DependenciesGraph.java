package dependencies;

import utils.Color;

import java.util.LinkedList;
import java.util.List;

public class DependenciesGraph implements TopologicalSortable {
    final private List<Vertex> vertices;
    final private List<Edge> edges;

    public DependenciesGraph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }

    DependenciesGraph(List<Vertex> originVertices, List<Edge> originEdges) {
        vertices = originVertices;
        edges = originEdges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public String sortingIsPossible() {
        String incorrectFile = "";

        for (int i = 0; i < vertices.size(); ++i) {
            incorrectFile = sortingIsPossibleHelp(vertices.get(i),
                    new DependenciesGraph(vertices, edges));
            if (!incorrectFile.equals("")) {
                break;
            }
        }

        recolorInWhite();
        return incorrectFile;
    }

    void recolorInWhite() {
        // for (int i = 0; i < vertices.size(); i++) {
        //     vertices.get(i).setColor(Color.WHITE);
        // }

        for (var vertex: vertices) {
            vertex.setColor(Color.WHITE);
        }

//        for (int i = 0; i < edges.size(); i++) {
//            edges.get(i).getSource().setColor(Color.WHITE);
//            edges.get(i).getDestination().setColor(Color.WHITE);
//        }

        for (var edge : edges) {
            edge.getSource().setColor(Color.WHITE);
            edge.getDestination().setColor(Color.WHITE);
        }
    }

    public List<Vertex> topologicalSorting() {
        List<Vertex> result = new LinkedList<>();

        for (int i = 0; i < vertices.size(); ++i) {
            topologicalSortingHelp(vertices.get(i), result,
                    new DependenciesGraph(vertices, edges));
        }

        return result;
    }
}