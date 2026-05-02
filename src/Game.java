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

    // Game world
    private World world;

    // Player
    private Player player;

    @Override
    public void start() {
        // Create the window and a camera for the window
        window = new Window(1280, 720);
        camera = new Camera(window, 75);

        // Give it an initial position and angle
        camera.setPosition(-2, 2, 4);
        camera.setAngle(25, -15);

        world = new World(new WorldGeneratorFlat(),10, 10, 10);
        player = new Player(world, camera, new Vector3(5.5f, 5, 5.5f));
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

            // Update the player
            player.update();

            // Run controls checks for moving the camera
            controls();

            // Update the window with everything we have done so far
            window.update();
        }
    }

    private void controls() {
        // camera controls for moving
        if(window.getKey(GLFW_KEY_W)) {
            // move forward
            player.accelerate(camera.getDirection(0, camera.getPitch()).scl(0.003f));
        }
        if(window.getKey(GLFW_KEY_S)) {
            // move forward
            player.accelerate(camera.getDirection(0, camera.getPitch()).scl(-0.003f));
        }
        if(window.getKey(GLFW_KEY_D)) {
            // move forward
            player.accelerate(camera.getDirection(90, camera.getPitch()).scl(-0.003f));
        }
        if(window.getKey(GLFW_KEY_A)) {
            // move forward
            player.accelerate(camera.getDirection(-90, camera.getPitch()).scl(-0.003f));
        }
        if(window.getKey(GLFW_KEY_SPACE)) {
            if(player.isOnGround()) {
                player.accelerate(new Vector3(0, 0.02f, 0));
            }
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

        if(window.getKey(GLFW_KEY_R)) {
            player.setPosition(new Vector3(5.5f, 5, 5.5f));
            player.resetVelocity();
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
