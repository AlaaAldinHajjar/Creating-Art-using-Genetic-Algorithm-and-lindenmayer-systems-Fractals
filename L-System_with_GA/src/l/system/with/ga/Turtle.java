package l.system.with.ga;

import gui.Canvas;

import java.awt.geom.Line2D;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Implements simple turtle graphics.
 *
 *
 *
 */
public class Turtle {

    /**
     * Minimum distance from the border to start drawing from.
     */
    private static final int OFFSET = 10;
    /**
     * Vertical starting angle.
     */
    private static final int V_ANGLE = 180;
    /**
     * Horizontal starting angle.
     */
    private static final int H_ANGLE = 90;

    /**
     * Current turtle state.
     */
    private TurtleState currentState;
    /**
     * States stack.
     */
    private Stack<TurtleState> stack;
    /**
     * Canvas to draw on.
     */
    private Canvas canvas;
    /**
     * State to draw.
     */
    private String state;
    /**
     * Turning angle.
     */
    private int angle;
    /**
     * Drawing length for each line.
     */
    private int lineLength;


    private int[][] color = new int[3][3];

    /**
     * Constructor.
     *
     * @param canvas Canvas to draw on.
     */
    public Turtle(final Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Changes the properties, used to start a new drawing.
     *
     * @param state System state to draw.
     * @param angle Turning angle.
     * @param lineLength Drawing length for each line.
     * @param startPosition Starting position (1 center, 2 corner, 3 bottom).
     * @param vertical True if the starting angle is vertical.
     */
    public final void setProperties(final String state, final int angle,
            final int lineLength, final int startPosition,
            final boolean vertical, final int[][] color) {
        double canvasWidth = canvas.getSize().width - canvas.getInsets().left
                - canvas.getInsets().right;
        double canvasHeight = canvas.getSize().height
                - canvas.getInsets().bottom - canvas.getInsets().top;
        int startAngle = vertical ? V_ANGLE : H_ANGLE;

        if (startPosition == 1) // Center
        {
            this.currentState = new TurtleState(canvasWidth / 2,
                    canvasHeight / 2, startAngle);
        } else if (startPosition == 2) // Corner
        {
            this.currentState = new TurtleState(OFFSET, canvasHeight
                    + canvas.getInsets().bottom - OFFSET, startAngle);
        } else // Bottom
        {
            this.currentState = new TurtleState(canvasWidth / 2, canvasHeight
                    + canvas.getInsets().bottom - OFFSET, startAngle);
        }

        this.stack = new Stack<TurtleState>();
        this.state = state;
        this.angle = angle;
        this.lineLength = lineLength;
        this.color=color;
    }

    /**
     * Draws the current system, tick by tick.
     *
     */
    public final void draw() {

        canvas.clear();
        for (char item : state.toCharArray()) {
            if (item == 'F' || item == 'G') {
                double nextX = currentState.x + (lineLength
                        * Math.sin(Math.toRadians(
                                currentState.angle)));
                double nextY = currentState.y + (lineLength
                        * Math.cos(Math.toRadians(
                                currentState.angle)));
                canvas.draw(new Line2D.Double(currentState.x,
                        currentState.y, nextX, nextY),color[(int)(ThreadLocalRandom.current().nextDouble()*3)]);
                currentState = new TurtleState(nextX, nextY,
                        currentState.angle);
            } else if (item == '+') {
                int nextAngle = currentState.angle + angle;
                currentState = new TurtleState(currentState.x,
                        currentState.y, nextAngle);
            } else if (item == '-') {
                int nextAngle = currentState.angle - angle;
                currentState = new TurtleState(currentState.x,
                        currentState.y, nextAngle);
            } else if (item == '[') {
                stack.push(currentState);
            } else if (item == ']') {
                if (!stack.isEmpty()) {
                    currentState = stack.pop();
                }
            }
        }
    }
}

/**
 * Stores the state of the turtle in a given moment.
 */
class TurtleState {

    /**
     * X coordinate.
     */
    public double x;
    /**
     * Y coordinate.
     */
    public double y;
    /**
     * Angle.
     */
    public int angle;

    /**
     * Constructor.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param angle Angle.
     */
    TurtleState(final double x, final double y, final int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
}
