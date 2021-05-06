package model;

import view.MainFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class LinearFunction implements Runnable/*, Function*/ {

    public double x;
    public double beginI;
    public double endI;
    private Lock lock;
    private GraphicPoint firstPoint;
    private GraphicPoint secondPoint;
    public static final int FUNCTION_ID = 0;
    private List<GraphicPoint> data;
    private double step;
    private MainFrame frame;

    public LinearFunction(Lock lock, MainFrame frame) {
        this.lock = lock;
        beginI = 0;
        endI = 50;
        this.data = new ArrayList<>();
        this.step = 0.1;
        this.frame = frame;
    }

    public double function(double x) {
        return 5 * x - 3;
    }


    @Override
    public void run() {
        double beginX = beginI;
        double endX = endI;
        double tempFx = 0;
        for (double x = beginX; x <= endX; x += step) {
            tempFx = function(x);
            tempFx = Math.round(tempFx * 10d) / 10d;
            x = Math.round(x * 10d) / 10d;
            secondPoint = new GraphicPoint(x, tempFx);
            data.add(secondPoint);
            lock.lock();
            try {
                frame.getGraphic().addValue(FUNCTION_ID, secondPoint);
                frame.getGraphic().repaint();
            } finally {
                lock.unlock();
            }
        }

    }


    public List<GraphicPoint> getData() {
        List<GraphicPoint> result;
        result = this.data;
        return result;
    }
}
