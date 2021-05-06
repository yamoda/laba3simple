package view;

import model.GraphicPoint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PointsTable {
    public Vector<String> columns;
    public DefaultTableModel model;
    public JTable table;
    public JScrollPane scrollPane;
    public MainFrame mainFrame;

    public PointsTable(MainFrame mainFrame) {
        columns = new Vector<String>();
        model = new DefaultTableModel(columns, 0);
        this.mainFrame = mainFrame;
    }

    JScrollPane buildComponent() {
        columns.add("x");
        columns.add("y");
        table = new JTable();
        table.setModel(model);
        scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(150, 350));
        return scrollPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void addValue(GraphicPoint point) {
        Vector<Double> vector = new Vector<>();
        vector.add(point.getX());
        vector.add(point.getY());
        model.addRow(vector);
    }

    public void clear() {
        model = new DefaultTableModel(columns, 0);
        table.setModel(model);
    }

    /*public void update() {

        List<List<GraphicPoint>> newValues = mainFrame.getValues();
        model = new DefaultTableModel(columns, 0);
        table.setModel(model);
        for (int index = 0; index < mainFrame.getValues().size(); index++) {
            Vector<GraphicPoint> vector = new Vector<>();
            vector.add(newValues.get(index).get(0));
            vector.add(newValues.get(index).get(1));
            model.addRow(vector);
        }
    }*/
}
