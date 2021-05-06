package view;

import controller.Controller;
import model.GraphicPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicComponent extends JPanel {

    private int width = 600;
    private int height = 500;
    public List<GraphicPoint> valuesLinear;
    public int fontSize;
    public Dimension firstSize;
    public Dimension size;
    public Dimension center;
    public int initialFontSize = 9;
    private Controller controller;
    private Graphics graph;
    private List<List<GraphicPoint>> functionsData;
    private int UNIT_SEGMENT_COEFFICIENT = 15;
    private int EXTEND_RATIO = 2000;

    public GraphicComponent(Controller controller) {
        valuesLinear = new ArrayList<GraphicPoint>();
        size = new Dimension(width, height);
        center = new Dimension(width / 2, height / 2);
        setPreferredSize(size);
        setSize(size);
        fontSize = 8;
        firstSize = new Dimension(600, 500);
        this.controller = controller;
        functionsData = new ArrayList<>();
        functionsData.add(new ArrayList<>());
        functionsData.add(new ArrayList<>());
    }

    public void incrementUnitSegment() {
        if (UNIT_SEGMENT_COEFFICIENT < 30)
            this.UNIT_SEGMENT_COEFFICIENT++;
    }

    public void decrementUnitSegment() {
        if (UNIT_SEGMENT_COEFFICIENT > 10)
            this.UNIT_SEGMENT_COEFFICIENT--;
    }

    private void drawAxis(Graphics graph) {
        center = new Dimension(size.width / 2, size.height / 2);
        graph.drawLine(10, size.height / 2, size.width - 1, size.height / 2);
        graph.drawLine(size.width / 2, 10, size.width / 2, size.height - 1);
        graph.drawLine(size.width - 30, size.height / 2 - 10, size.width - 1, size.height / 2);
        graph.drawLine(size.width - 30, size.height / 2 + 10, size.width - 1, size.height / 2);
        graph.drawLine(size.width / 2, 10, size.width / 2 - 10, 30);
        graph.drawLine(size.width / 2, 10, size.width / 2 + 10, 30);
        graph.drawString("X", size.width - 20, size.height / 2 + 20);
        graph.drawString("Y", size.width / 2 - 20, 20);
        graph.drawString("0", size.width / 2 - 10, size.height / 2 + 10);
        int counter = 1;
        for (int index = (int) size.getWidth() / 2; index < size.width; index += UNIT_SEGMENT_COEFFICIENT) {
            graph.drawLine((index), size.height / 2, (index),
                    size.height / 2 + 3);
            graph.drawString(Integer.toString(counter), index + UNIT_SEGMENT_COEFFICIENT, (int) size.getHeight() / 2 - 5);
            counter++;
        }
        counter = 1;
        for (int index = (int) size.getHeight() / 2; index < size.height; index += UNIT_SEGMENT_COEFFICIENT) {
            graph.drawLine(size.width / 2 - 3, index, size.width / 2, index);
            graph.drawString(Integer.toString(counter), (int) size.getWidth() / 2, index + UNIT_SEGMENT_COEFFICIENT);
            counter++;
        }
        counter = -1;
        for (int index = (int) size.getWidth() / 2; index > 0; index -= UNIT_SEGMENT_COEFFICIENT) {
            graph.drawLine((index), size.height / 2, (index),
                    size.height / 2 + 3);
            graph.drawString(Integer.toString(counter), index - UNIT_SEGMENT_COEFFICIENT, (int) size.getHeight() / 2 - 5);
            counter--;
        }
        counter = -1;
        for (int index = (int) size.getHeight() / 2; index > 0; index -= UNIT_SEGMENT_COEFFICIENT) {
            graph.drawLine(size.width / 2 - 3, index, size.width / 2, index);
            graph.drawString(Integer.toString(counter), (int) size.getWidth() / 2, index - UNIT_SEGMENT_COEFFICIENT);
            counter--;
        }
    }

    public void drawFunction(List<GraphicPoint> values) {
        for (int index = 1; index < values.size(); index++) {
            double tempFx = (values.get(index)).getY();
            double tempX = (values.get(index)).getX();
            double prevFx = (values.get(index - 1)).getY();
            double prevX = (values.get(index - 1)).getX();
            int newFx = (int) (UNIT_SEGMENT_COEFFICIENT * tempFx);
            int newX = (int) (UNIT_SEGMENT_COEFFICIENT * tempX);
            int newprevFx = (int) (UNIT_SEGMENT_COEFFICIENT * prevFx);
            int newPrevX = (int) (UNIT_SEGMENT_COEFFICIENT * prevX);
            graph.setColor(Color.BLUE);
            int drawPrevX = center.width + newPrevX;
            int drawPrevY = center.height - newprevFx;
            int drawX = center.width + newX;
            int drawY = center.height - newFx;
            graph.drawLine(drawPrevX, drawPrevY, drawX, drawY);
            graph.setColor(Color.DARK_GRAY);
        }
    }

    private void extendChart(int pointX, int pointY) {
        while (pointX * UNIT_SEGMENT_COEFFICIENT > getSize().getWidth() / 2 || pointY * UNIT_SEGMENT_COEFFICIENT > getSize().getHeight() / 2) {
            setSize((int) getSize().getWidth() + EXTEND_RATIO,
                    (int) getSize().getHeight() + EXTEND_RATIO);
            this.size = getSize();
            setPreferredSize(size);
            revalidate();
        }
    }

    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        graphic.setColor(Color.DARK_GRAY);
        size = new Dimension(this.getWidth(), this.getHeight());
        Graphics2D graph = (Graphics2D) graphic;
        this.graph = graph;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setFont(new Font("Comic Sans", Font.PLAIN, fontSize));
        drawAxis(graph);
        drawFunction(valuesLinear);
        for (List<GraphicPoint> function : functionsData) {
            drawFunction(function);
        }
    }

    public void drawPoints(GraphicPoint firstPoint, GraphicPoint secondPoint) {
        int drawPrevX = /*center.width + 4 * */(int) firstPoint.getX();
        int drawPrevY = /*center.height - */(int) firstPoint.getY();
        int drawX = /*center.width + 4 * */(int) secondPoint.getX();
        int drawY = /*center.height - */(int) secondPoint.getY();
        graph.drawLine(drawPrevX,
                drawPrevY,
                drawX,
                drawY);
    }

    public List<GraphicPoint> getValuesLinear() {
        return valuesLinear;
    }


    public void clear() {
        this.size = firstSize;
        setSize(size);
        setPreferredSize(size);
        revalidate();
        functionsData.clear();
        functionsData.add(new ArrayList<>());
        functionsData.add(new ArrayList<>());
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getInitialFontSize() {
        return initialFontSize;
    }

    public Dimension getFirstSize() {
        return firstSize;
    }

    public void changeFirstSize(Dimension tempSize) {
        firstSize = tempSize;
    }

    public void addValue(int id, GraphicPoint point) {
        functionsData.get(id).add(point);
        extendChart((int) point.getX(), (int) point.getY());
    }
}
