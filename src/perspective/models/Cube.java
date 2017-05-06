package perspective.models;

import java.awt.*;

/**
 * Created by master Alish on 5/6/17.
 */
public class Cube {
    private Coord coord;
    private float size;
    private Color color;

    public Cube(Coord coord, float size, Color color){
        this.color = color;
        this.coord = coord;
        this.size = size;
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
}
