package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Implements a canvas to draw lines on.
 *
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

    /**
     * List of lines to draw onto the canvas.
     */
    /**
     * Adds a line to the canvas.
     *
     * @param line Line to add to the canvas.
     */
    /**
     * Draws current content of the canvas.
     */
    Color myColor = new Color(0,0,0);
    public final void draw(final Line2D.Double line, int[] color) {
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        myColor = new Color(color[0], color[1], color[2]);
        graphics2D.setColor(myColor);
        graphics2D.drawLine((int) Math.round(line.getX1()), (int) Math.round(line.getY1()),
                (int) Math.round(line.getX2()), (int) Math.round(line.getY2()));
    }

    /**
     * Clears the canvas and redraws background and border.
     */
    public final void clear() {
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        graphics2D.setColor(Color.white);
        graphics2D.clearRect(1, 1, getWidth() - 2, getHeight() - 2);
        graphics2D.fillRect(2, 2, getWidth() - 3, getHeight() - 3);
    }

    @Override
    public final void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

    }
}
