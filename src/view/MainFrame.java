package view;

import controller.Controller;
import model.GraphicPoint;
import model.LinearFunction;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MainFrame {
    public JFrame frame;
    public Controller controller;
    private int width = 1200;
    private int height = 800;
    public double tempXBeg, tempXEnd;
    private TaskPanel taskPanel;
    private PointsTable mainPointsTable;
    private GraphicComponent graphic;
    public LinearFunction calc;
    public JScrollPane scroll;
    private ReentrantLock lock;

    public GraphicComponent getGraphic() {
        return graphic;
    }

    public TaskPanel getTaskPanel() {
        return taskPanel;
    }

    public PointsTable getMainPointsTable() {
        return mainPointsTable;
    }

    public MainFrame() {
        lock = new ReentrantLock();
        controller = new Controller(MainFrame.this, lock);
        frame = new JFrame();
        taskPanel = new TaskPanel();
    }

    public JFrame buildFrame() {
        frame.setTitle("Function Drawing =^_^=");
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout());
        graphic = new GraphicComponent(controller);
        mainPointsTable = new PointsTable(this);
        scroll = new JScrollPane(graphic);
        scroll.setPreferredSize(new Dimension(605, 505));
        scroll.setAutoscrolls(true);
        scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        frame.add(mainPointsTable.buildComponent(), BorderLayout.WEST);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(taskPanel.buildComponent(), BorderLayout.EAST);
        HoldAndDragListener listener = new HoldAndDragListener(graphic);
        scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        scroll.getViewport().addMouseListener(listener);
        scroll.getViewport().addMouseMotionListener(listener);
        ZoomListener zoomListener = new ZoomListener(MainFrame.this, graphic, taskPanel);
        scroll.addMouseWheelListener(zoomListener);
        taskPanel.getMainButton().addActionListener(event -> {
            try {
                controller.stopThreads();
                mainPointsTable.clear();
                graphic.clear();
                startCalculation();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        taskPanel.getButtonStop().addActionListener(event -> {
            controller.stopThreads();
            mainPointsTable.clear();
            graphic.clear();
        });
        return frame;
    }

    public void startCalculation() throws InterruptedException {
        controller.startLinearFunctionThread();
        controller.startSortFunctionThread(taskPanel.getN(), taskPanel.getK());
    }


    public void repaintFrame() {
        frame.repaint();
    }

    public List<GraphicPoint> getValues() {
        return graphic.getValuesLinear();
    }

    public void clear() {
        graphic.clear();
    }

    public void show() {
        frame.setVisible(true);
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
