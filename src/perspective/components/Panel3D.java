package perspective.components;

import perspective.models.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by master Alish on 5/6/17.
 */
public class Panel3D extends JPanel {
    private final int zeroXPercent = 50;
    private final int zeroYPercent = 10;
    private float xScale = 40;
    private float yScale = 40;

    private int angle = 0;
    private Coord coord = new Coord(-3f, 0, 0);
    private WidthHeight size;
    private Xy center;
    private Xy zero;
    private Xy leftPoint;
    private Color linesColor = new Color(75, 75, 75);

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        this.size = new WidthHeight(getWidth(), getHeight());
        this.center = new Xy(getWidth()/2, getHeight()/2);
        this.zero = new Xy((size.w*zeroXPercent)/100, (size.h*(100-zeroYPercent))/100);
        this.leftPoint = new Xy(0, center.y);

        drawHorizonAndCenter(g);
    }

    private void drawHorizonAndCenter(Graphics g) {
        g.setColor(linesColor);
        g.drawLine(0, center.y, size.w, center.y);

        drawHorizontalGrid(g, 0f);

        drawRedDot(g, center.x, center.y);
        drawRedDot(g, zero.x, zero.y);
        drawRedDot(g, leftPoint.x, leftPoint.y);
    }

    private void drawHorizontalGrid(Graphics g, float yCoord) {
        g.setColor(linesColor);

        int y = convertY(yCoord);

        for(float x=-10; x<=10;x+=1){
            g.drawLine(center.x, center.y, convertX(x), y);
        }

        drawCube(g, new Cube(coord, 2.5f, Color.red));

        drawPyramid(g, new Pyramid(new Coord(-4, 0, 2), 2.5f, 2f, Color.orange), angle);
        drawPyramid(g, new Pyramid(new Coord(-4, 0, 6), 2.5f, 2f, Color.orange), angle);
        drawPyramid(g, new Pyramid(new Coord(-4, 0, 10), 2.5f, 2f, Color.orange), angle);
        drawPyramid(g, new Pyramid(new Coord(-4, 0, 14), 2.5f, 2f, Color.orange), angle);

        drawPyramid(g, new Pyramid(new Coord(-8, 0, 2), 2.5f, 2f, Color.orange), angle);
        drawPyramid(g, new Pyramid(new Coord(-8, 0, 6), 2.5f, 2f, Color.orange), angle);
        drawPyramid(g, new Pyramid(new Coord(-8, 0, 10), 2.5f, 2f, Color.orange), angle);
        drawPyramid(g, new Pyramid(new Coord(-8, 0, 14), 2.5f, 2f, Color.orange), angle);

    }

    private Xy convert(Coord coords){
        Xy xy = new Xy(zero.x, zero.y);

        // Moving by Z
        int offsetYForZ = convertZ(coords.z);
        xy.y -= offsetYForZ;

        // Moving by X
        int dyToZero = zero.y - center.y;
        xy.x += (float)(convertX(coords.x) - zero.x)/ (float)dyToZero * (float)(dyToZero - offsetYForZ);

        // Moving by Y
        xy.y -= (float)(dyToZero-offsetYForZ)/(float)dyToZero * yScale * coords.y;

        return xy;
    }

    private int convertX(float xCoord) {
        return (int)(zero.x + xCoord*xScale);
    }

    private int convertY(float yCoord) {
        return (int) (zero.y - yCoord*yScale);
    }

    private int convertZ(float zCoord) {
        int a_ = center.x - leftPoint.x;
        int a = convertX(zCoord) - zero.x;
        return (int) ((float)a/(float)(a+a_) * (float)(zero.y - center.y));
    }

    private void drawRedDot(Graphics g, int x, int y) {
        g.setColor(Color.red);
        g.fillOval(x-2, y-2, 4, 4);

        g.setColor(linesColor);
        g.drawOval(x-2, y-2, 4, 4);
    }

    private void drawDot(Graphics g, Xy xy, Color color) {
        g.setColor(color);
        g.fillOval(xy.x-2, xy.y-2, 4, 4);

        g.setColor(linesColor);
        g.drawOval(xy.x-2, xy.y-2, 4, 4);
    }

    private void drawLine(Graphics g, Xy xy1, Xy xy2, Color color) {
        g.setColor(color);
        g.drawLine(xy1.x, xy1.y, xy2.x, xy2.y);
    }

    private void drawRect(Graphics g, Coord leftBottom, Coord rightBottom, Coord leftTop, Coord rightTop, Color color){
        Xy xy1 = convert(leftBottom);
        Xy xy2 = convert(rightBottom);
        Xy xy3 = convert(leftTop);
        Xy xy4 = convert(rightTop);

        drawLine(g, xy1, xy2, color);
        drawLine(g, xy4, xy2, color);
        drawLine(g, xy1, xy3, color);
        drawLine(g, xy4, xy3, color);
    }

    private void drawTriangle(Graphics g, Coord first, Coord second, Coord third, Color color){
        Xy xy1 = convert(first);
        Xy xy2 = convert(second);
        Xy xy3 = convert(third);

        drawLine(g, xy1, xy2, color);
        drawLine(g, xy3, xy2, color);
        drawLine(g, xy1, xy3, color);
    }

    private void drawCube(Graphics g, Cube cube) {
        Coord c = cube.getCoord();
        Color color = cube.getColor();
        float size = cube.getSize();

        Xy rbf = convert(c);
        Xy lbf = convert(new Coord(c.x + size, c.y, c.z));
        Xy rtf = convert(new Coord(c.x, c.y + size, c.z));
        Xy ltf = convert(new Coord(c.x + size, c.y + size, c.z));

        Xy rbb = convert(new Coord(c.x, c.y, c.z + size));
        Xy lbb = convert(new Coord(c.x + size, c.y, c.z + size));
        Xy rtb = convert(new Coord(c.x, c.y + size, c.z + size));
        Xy ltb = convert(new Coord(c.x + size, c.y + size, c.z + size));

        drawLine(g, rbf, lbf, color);
        drawLine(g, rbf, rtf, color);
        drawLine(g, ltf, rtf, color);
        drawLine(g, ltf, lbf, color);

        drawLine(g, rbb, lbb, color);
        drawLine(g, rbb, rtb, color);
        drawLine(g, ltb, rtb, color);
        drawLine(g, ltb, lbb, color);

        drawLine(g, ltb, ltf, color);
        drawLine(g, lbb, lbf, color);
        drawLine(g, rbb, rbf, color);
        drawLine(g, rtb, rtf, color);
    }

    private void drawPyramid(Graphics g, Pyramid pyramid) {
        Coord lf = pyramid.getCoord();

        Coord rf = new Coord(lf.x + pyramid.getSize(), lf.y, lf.z);
        Coord lb = new Coord(lf.x, lf.y, lf.z+ pyramid.getSize());
        Coord rb = new Coord(lf.x + pyramid.getSize(), lf.y, lf.z + pyramid.getSize());
        Coord top = new Coord(lf.x + pyramid.getSize()/2f, lf.y + pyramid.getHeight(), lf.z + pyramid.getSize()/2f);

        drawTriangle(g, lf, rf, top, pyramid.getColor());
        drawTriangle(g, lf, lb, top, pyramid.getColor());
        drawTriangle(g, rb, lb, top, pyramid.getColor());
        drawTriangle(g, rf, rb, top, pyramid.getColor());
    }

    private void drawPyramid(Graphics g, Pyramid pyramid, int angle) {
        Coord point = pyramid.getCoord();
        float radius = (float) Math.sqrt(pyramid.getSize()/2*pyramid.getSize()/2*2);
        Coord center = new Coord(point.x + pyramid.getSize()/2f, point.y, point.z + pyramid.getSize()/2f);

        Coord lf = new Coord(center.x+cos(45+angle) * radius, center.y, center.z+sin(45+angle) * radius);
        Coord rf = new Coord(center.x+cos(45+270+angle) * radius, center.y, center.z+sin(45+270+angle) * radius);
        Coord lb = new Coord(center.x+cos(45+90+angle) * radius, center.y, center.z+sin(45+90+angle) * radius);
        Coord rb = new Coord(center.x+cos(45+180+angle) * radius, center.y, center.z+sin(45+180+angle) * radius);

        Coord top = new Coord(center.x, center.y + pyramid.getHeight(), center.z);

        drawTriangle(g, lf, rf, top, pyramid.getColor());
        drawTriangle(g, lf, lb, top, pyramid.getColor());
        drawTriangle(g, rb, lb, top, pyramid.getColor());
        drawTriangle(g, rf, rb, top, pyramid.getColor());
    }

    private float cos(int angle) {
        return (float) Math.cos(angle/(180f/Math.PI));
    }

    private float sin(int angle) {
        return (float) Math.sin(angle/(180f/Math.PI));
    }

    public void tick() {
        angle+=1;
        if(angle%400 < 100){
            coord.x += 0.05;
        }else if(angle%400 < 200){
            coord.y += 0.05;
        }else if(angle%400 < 300){
            coord.x -= 0.05;
        }else{
            coord.y -= 0.05;
        }
        repaint();
    }
}
