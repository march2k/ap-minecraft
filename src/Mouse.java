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
    private static float sensitivity = 0.1f;
    private DoubleBuffer xp, yp;
    private double lastX, lastY;
    private float dx, dy;
    private long handle;

    public Mouse(long handle) {
        xp = BufferUtils.createDoubleBuffer(1);
        yp = BufferUtils.createDoubleBuffer(1);
        this.handle = handle;
    }

    public void update() {
        glfwGetCursorPos(handle, xp, yp);

        float curX = (float)xp.get();
        float curY = (float)yp.get();

        xp.rewind();
        yp.rewind();

        dx = curX - (float) lastX;
        dy = curY - (float) lastY;

        lastX = xp.get();
        lastY = yp.get();

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
