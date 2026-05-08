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

    // Delta time (time between the last frame and this frame)
    // Used for making sure that physics and motion occurs at
    // the same speed no matter the game's frame rate
    private static float deltaTime;
    private double lastFrameTime;

    // Game world
    private World world;

    // Player
    private Player player;

    @Override
    public void start() {
        // Create the window and a camera for the window
        window = new Window(1280, 720);
        camera = new Camera(window, 80);

        // Give it an initial position and angle
        camera.setPosition(-2, 2, 4);
        camera.setAngle(25, -15);

        world = new World(new WorldGeneratorFlat(),10, 10, 10);
        player = new Player(camera, world, new Vector3(5.5f, 7, 5.5f));
    }

    @Override
    public void loop() {
        // Run this code until we need to close the window
        while(window.isOpen()) {
            // Calculate delta time
            double curTime = glfwGetTime();
            deltaTime = (float)(curTime - lastFrameTime);
            lastFrameTime = curTime;

            // Clear the screen with this color
            window.clear(0.5, 0.2, 0.8);

            // Calculate the new projection matrix based on the camera's position
            // and its rotation. Expensive, but it needs to be done every frame
            camera.calculate();

            world.draw();
            player.update();

            // Run controls checks for moving the camera
            controls();

            // Update the window with everything we have done so far
            window.update();
        }
    }

    private void controls() {
        // camera controls for looking around
        float dx = window.getDeltaX();
        float dy = window.getDeltaY();
        float sens = Mouse.getSensitivity();
        camera.rotate(dx * sens, -dy * sens);

        // Precalculate all these vectors so we don't have to do it when we are
        // trying to actually move the player
        Vector3 forward = camera.getDirection(0, camera.getPitch());
        Vector3 right = camera.getDirection(-90, camera.getPitch());

        // Some floats that we are going to need for movement
        float dt = getDeltaTime();
        float speed = player.getSpeed();

        // Create a new empty Vector3 and add all the different moves that we want
        // to do together into it (for example, add what would have been added straight
        // to the player's velocity to this vector, so if someone was pressing the
        // W and A keys down, add the directions for both the W and the A key to this.)
        Vector3 move = new Vector3();

        if(window.getKey(GLFW_KEY_W)) {
            move.add(forward);
        }
        if(window.getKey(GLFW_KEY_S)) {
            move.add(forward.scl(-1));
        }
        if(window.getKey(GLFW_KEY_D)) {
            move.add(right);
        }
        if(window.getKey(GLFW_KEY_A)) {
            move.add(right.scl(-1));
        }

        // Here is the best part right here. I giggled when this code worked
        // Normalize the entire vector with all the desired moves to make it so that
        // pressing down two keys (going diagonally) is not any faster than just
        // walking or strafing. Then, scale by the speed and finally by deltaTime.
        move.normalize().scl(speed).scl(dt);
        player.accelerate(move);

        // Basic stupid jump code
        if(window.getKey(GLFW_KEY_SPACE)) {
            if(player.isOnGround()) {
                player.accelerate(new Vector3(0, player.getJumpPower(), 0).scl(0.015f));
            }
        }

        // escape to exit game
        if(window.getKey(GLFW_KEY_ESCAPE)) {
            window.close();
        }
    }

    @Override
    public void stop() {
        window.destroy();
    }

    public static float getDeltaTime() {
        return deltaTime;
    }

    public static void main(String[] args) {
        new ApplicationWrapper(new Game()).launch();
    }
}
