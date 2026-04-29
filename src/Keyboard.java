import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

/*
Keyboard.java - Thomas

The Keyboard class is used internally by the Window to keep track of key press and release
events and therefore track the current state of keys (pressed event but not released event
means that it is pressed down, etc.) It allows the Window to see what keys are held down.
 */

public class Keyboard implements GLFWKeyCallbackI {
    private boolean[] keys;

    public Keyboard(long handle) {
        glfwSetKeyCallback(handle, this);
        keys = new boolean[GLFW_KEY_LAST];
    }

    @Override
    public void invoke(long win, int key, int sc, int action, int mods) {
        if(action == GLFW_PRESS) {
            keys[key] = true;
        }
        if(action == GLFW_RELEASE) {
            keys[key] = false;
        }
    }

    public boolean getKey(int key) {
        return keys[key];
    }
}
