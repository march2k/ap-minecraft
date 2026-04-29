import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private long handle;
    private int width;
    private int height;

    private Keyboard keyboard;

    public Window(int w, int h) {
        glfwInit();
        handle = glfwCreateWindow(w, h, "cccc", 0L, 0L);
        glfwMakeContextCurrent(handle);
        glfwSwapInterval(1);
        GL.createCapabilities();

        width = w;
        height = h;

        keyboard = new Keyboard(handle);
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(handle);
    }

    public void clear(double r, double g, double b) {
        glClearColor((float)r, (float)g, (float)b, 1);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(handle);
    }

    public boolean getKey(int key) {
        return keyboard.getKey(key);
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
