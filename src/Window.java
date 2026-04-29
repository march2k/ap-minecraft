import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/*
Window.java - Thomas

The Window class represents a GLFW window and its handle, and all of its properties like
width, height, and keyboard/mouse data. I suppose a limitation is that it does not keep
track of its current title. The Window is responsible for keeping track of the Keyboard
and Mouse data that it generates and providing it to calling methods.
 */

public class Window {
    private long handle;
    private int width;
    private int height;

    private Keyboard keyboard;
    private Mouse mouse;

    public Window(int w, int h) {
        glfwInit();

        handle = glfwCreateWindow(w, h, "cccc", 0L, 0L);
        glfwMakeContextCurrent(handle);
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSwapInterval(1);

        if(glfwRawMouseMotionSupported()) {
            glfwSetInputMode(handle, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
        }

        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        width = w;
        height = h;

        keyboard = new Keyboard(handle);
        mouse = new Mouse(handle);
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(handle);
    }

    public void clear(double r, double g, double b) {
        glClearColor((float)r, (float)g, (float)b, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(handle);
        mouse.update();
    }

    public void close() {
        glfwSetWindowShouldClose(handle, true);
    }

    public boolean getKey(int key) {
        return keyboard.getKey(key);
    }

    public float getDeltaX() {
        return mouse.getDeltaX();
    }

    public float getDeltaY() {
        return mouse.getDeltaY();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void destroy() {
        glfwDestroyWindow(handle);
        glfwTerminate();
    }
}
