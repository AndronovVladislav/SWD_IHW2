package dependencies;

public class Edge {
    Vertex source, destination;

    public Edge(Vertex src, Vertex dest) {
        source = src;
        destination = dest;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }
}
