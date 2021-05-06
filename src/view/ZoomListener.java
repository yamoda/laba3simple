package view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ZoomListener implements MouseWheelListener {

    public GraphicComponent graphic;
    public TaskPanel buttons;
    public MainFrame mainFrame;
    private int ZOOM_RATIO;

    public ZoomListener(MainFrame mainFrame, GraphicComponent graphic, TaskPanel buttons) {
        this.mainFrame = mainFrame;
        this.graphic = graphic;
        this.buttons = buttons;
        this.ZOOM_RATIO = 100;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (event.getPreciseWheelRotation() < 0 && KeyEvent.VK_CONTROL != 0) {
            /*Dimension newSize = new Dimension(graphic.getWidth() + ZOOM_RATIO,
                    graphic.getHeight() + ZOOM_RATIO);
            graphic.setPreferredSize(newSize);
            graphic.setSize(newSize);
            graphic.revalidate();*/
            graphic.incrementUnitSegment();
            graphic.repaint();
        }
        if (event.getPreciseWheelRotation() > 0 && KeyEvent.VK_CONTROL != 0) {
            /*if (graphic.getWidth() > graphic.firstSize.getWidth()) {
                /*Dimension newSize = new Dimension(graphic.getWidth() - ZOOM_RATIO,
                        graphic.getHeight() - ZOOM_RATIO);
                graphic.setPreferredSize(newSize);
                graphic.setSize(newSize);
                graphic.revalidate();*/
            graphic.decrementUnitSegment();
            graphic.repaint();
        }
    }
}
