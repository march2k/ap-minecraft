import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

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

    private int framebufferWidth;
    private int framebufferHeight;

    private Keyboard keyboard;
    private Mouse mouse;

    public Window(int w, int h) {
        glfwInit();

        // create a fullscreen window
        long monitor = glfwGetPrimaryMonitor();
        handle = glfwCreateWindow(w, h, "cccc", 0L, 0L);

        // use this window as the opengl context
        glfwMakeContextCurrent(handle);

        // lock the mouse into this window
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        // vsync
        glfwSwapInterval(1);

        // get the framebuffer size because we need this to make sure that the glViewport
        // is set properly for high DPI screens like 4k monitors or on Mac.
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(handle, widthBuffer, heightBuffer);
        framebufferWidth = widthBuffer.get();
        framebufferHeight = heightBuffer.get();

        // use raw mouse input
        if(glfwRawMouseMotionSupported()) {
            glfwSetInputMode(handle, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
        }

        // do this so that we can call opengl functions
        GL.createCapabilities();

        // turn on depth and textures
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

    public int getFramebufferWidth() {
        return framebufferWidth;
    }

    public int getFramebufferHeight() {
        return framebufferHeight;
    }

    public void destroy() {
        glfwDestroyWindow(handle);
        glfwTerminate();
    }
}
