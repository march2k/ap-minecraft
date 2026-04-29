import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game implements Application {
    private Window window;
    private Camera camera;
    private TestTriangle triangle;

    @Override
    public void start() {
        window = new Window(1024, 768);
        camera = new Camera(window, 45);
        camera.setPosition(0.5f, 0.5f, 5);

        triangle = new TestTriangle();
    }

    @Override
    public void loop() {
        while(window.isOpen()) {
            window.clear(0.5, 0.2, 0.8);
            camera.calculate();

            triangle.draw();

            window.update();
        }
    }

    @Override
    public void stop() {
        window.destroy();
    }

    public static void main(String[] args) {
        new ApplicationWrapper(new Game()).launch();
    }
}
