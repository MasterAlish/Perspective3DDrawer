package perspective.models;

import java.awt.*;

/**
 * Created by master Alish on 5/6/17.
 */
public class Pyramid {
    private Coord coord;
    private float size;
    private float height;
    private Color color;

    public Pyramid(Coord coord, float size, float height, Color color){
        this.color = color;
        this.coord = coord;
        this.size = size;
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public float getSize() {
        return size;
    }

    public Coord getCoord() {
        return coord;
    }

    public float getHeight() {
        return height;
    }
}
