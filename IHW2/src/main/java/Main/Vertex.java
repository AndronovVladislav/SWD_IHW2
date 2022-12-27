package main;

enum Color {
    WHITE,
    GRAY,
    BLACK
}

public class Vertex {
    String file;
    Color color;

    Vertex(String file) {
        this.file = file;
        this.color = Color.WHITE;
    }
}
