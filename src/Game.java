import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game {
    public static void main(String[] args) {
        glfwInit();
        long window = glfwCreateWindow(1024, 768, "Test", 0L, 0L);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        while(!glfwWindowShouldClose(window)) {
            glClearColor(0.5f, 0.3f, 0.8f, 1);
            glClear(GL_COLOR_BUFFER_BIT);
            glfwPollEvents();
            glfwSwapBuffers(window);
        }

        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
