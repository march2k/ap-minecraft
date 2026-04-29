import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

/*
Mouse.java - Thomas

The Mouse class is used internally by the Window to keep track of delta mouse movement,
which is the amount that the mouse position has changed since the last frame. This kind
of data is especially useful for the first person camera. It also is responsible for
keeping track of the mouse sensitivity.
 */

public class Mouse {
    // Mouse sensitivity when moving the camera
    private static float sensitivity = 0.1f;

    // DoubleBuffers used to read the x and y position of the mouse
    // from LWJGL, since it is internally a C function we need these
    private DoubleBuffer xp, yp;

    // The position of the mouse last frame
    private double lastX, lastY;

    // The actual deltas in X and Y of the mouse position
    private float dx, dy;

    // Handle to the window that we are bound to
    private long handle;

    public Mouse(long handle) {
        xp = BufferUtils.createDoubleBuffer(1);
        yp = BufferUtils.createDoubleBuffer(1);
        this.handle = handle;
    }

    public void update() {
        // Get current cursor position to the buffers
        glfwGetCursorPos(handle, xp, yp);

        // Grab the data into variables to actually use it
        float curX = (float)xp.get();
        float curY = (float)yp.get();

        // Calculate deltas based on last frame's positions
        dx = curX - (float) lastX;
        dy = curY - (float) lastY;

        // Update our memory of last frame's positions with this frame
        lastX = curX;
        lastY = curY;

        // Reset the buffers for the next frame to avoid overflow
        xp.clear();
        yp.clear();
    }

    public float getDeltaX() {
        return dx;
    }

    public float getDeltaY() {
        return dy;
    }

    public static float getSensitivity() {
        return sensitivity;
    }
}
