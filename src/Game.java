import static org.lwjgl.glfw.GLFW.*;

/*
Game.java - Thomas

This is the main class of the game, it handles communication between different components
of the game, for example telling the camera to move when the window reports a key is pressed.
It basically is responsible for coordinating all the other classes actions.
 */

public class Game implements Application {
    // Game window
    private Window window;

    // Perspective camera to see the world
    private Camera camera;

    private World world;

    @Override
    public void start() {
        // Create the window and a camera for the window
        window = new Window(1024, 768);
        camera = new Camera(window, 60);

        // Give it an initial position and angle
        camera.setPosition(-2, 2, 4);
        camera.setAngle(25, -15);

        world = new World(10, 10, 10);
    }

    @Override
    public void loop() {
        // Run this code until we need to close the window
        while(window.isOpen()) {
            // Clear the screen with this color
            window.clear(0.5, 0.2, 0.8);

            // Calculate the new projection matrix based on the camera's position
            // and its rotation. Expensive, but it needs to be done every frame
            camera.calculate();

            world.draw();

            // Run controls checks for moving the camera
            controls();

            // Update the window with everything we have done so far
            window.update();
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
