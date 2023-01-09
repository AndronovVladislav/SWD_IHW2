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

    private DependenciesGraph(List<Vertex> originVertices, List<Edge> originEdges) {
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
        SortingHelper sortingHelper = new SortingHelper(new DependenciesGraph(vertices, edges));

        for (int i = 0; i < vertices.size(); ++i) {
            incorrectFile = sortingHelper.sortingIsPossibleHelp(vertices.get(i));
            if (!incorrectFile.equals("")) {
                break;
            }
        }

        recolorInWhite();
        return incorrectFile;
    }

    void recolorInWhite() {
        for (var vertex : vertices) {
            vertex.setColor(Color.WHITE);
        }

        for (var edge : edges) {
            edge.getSource().setColor(Color.WHITE);
            edge.getDestination().setColor(Color.WHITE);
        }
    }

    public List<Vertex> topologicalSorting() {
        List<Vertex> result = new LinkedList<>();
        SortingHelper sortingHelper = new SortingHelper(new DependenciesGraph(vertices, edges));

        for (int i = 0; i < vertices.size(); ++i) {
            sortingHelper.topologicalSortingHelp(vertices.get(i), result);
        }

        recolorInWhite();
        return result;
    }
}