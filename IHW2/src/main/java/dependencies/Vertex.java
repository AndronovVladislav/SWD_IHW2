package dependencies;

import utils.Color;

public class Vertex {
    final private String filename;
    private Color color;

    public Vertex(String file) {
        filename = file;
        color = Color.WHITE;
    }
    public String getFilename() {
        return filename;
    }
    public void setColor(Color newColor) {
        color = newColor;
    }
    public Color getColor() {
        return color;
    }
}
