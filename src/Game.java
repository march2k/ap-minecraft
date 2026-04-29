import org.lwjgl.opengl.GL;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game implements Application {
    private Window window;
    private Camera camera;
    private Block block;
    private Block block2;

    @Override
    public void start() {
        window = new Window(1024, 768);
        camera = new Camera(window, 60);
        camera.setPosition(-2, 2, 4);
        camera.setAngle(25, -15);

        block = new Block("/dirt.png", "/grass.png");
        block2 = new Block("/cobblestone.png", "/cobblestone.png");
    }

    @Override
    public void loop() {
        while(window.isOpen()) {
            window.clear(0.5, 0.2, 0.8);
            camera.calculate();

            block.draw(2, 0, 0);
            block2.draw(1, 0, 0);
            controls();

            window.update(); // test
        }
    }

    private void controls() {
        // camera controls for moving (flight for now)
        if(window.getKey(GLFW_KEY_W)) {
            // move forward
            camera.translate(camera.getDirection().scl(0.05f));
        }
        if(window.getKey(GLFW_KEY_S)) {
            // move backwards (opposite of forwards)
            camera.translate(camera.getDirection().scl(-0.05f));
        }
        if(window.getKey(GLFW_KEY_A)) {
            // move left, remove pitch from calculation to prevent diagonal movement
            camera.translate(camera.getDirection(90, camera.getPitch()).scl(0.05f));
        }
        if(window.getKey(GLFW_KEY_D)) {
            // move right, do the same as for the A key
            camera.translate(camera.getDirection(-90, camera.getPitch()).scl(0.05f));
        }

        // camera controls for looking around
        float dx = window.getDeltaX();
        float dy = window.getDeltaY();
        float sens = Mouse.getSensitivity();
        camera.rotate(dx * sens, -dy * sens);

        // escape to exit game
        if(window.getKey(GLFW_KEY_ESCAPE)) {
            window.close();
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
