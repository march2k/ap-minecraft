import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

/*
Camera.java - Thomas

This class Camera implements a 3D perspective camera using some matrix math and glFrustum.
It also sets the glViewport (which changes what portion of the screen is used for drawing) and
translates and rotates the world to fit the camera's rotation and position. This is because there
is not actually a "camera" in openGL and everything is moved to mimic the effect.
 */

public class Camera {
    private Window window;

    // Vertical field of vision of this camera
    private float fov;

    // Height of near plane (how close to something can you get before clipping)
    private final float near = 0.1f;

    // Height of far plane (what is the render distance)
    private final float far = 100f;

    // Position of this camera in the world
    private float x, y, z;

    // Rotation of this camera in the world
    private float yaw, pitch;

    // Upper and lower limits to looking up and down. This prevents breaking your
    // neck and avoids gimbal lock from extreme values messing up matrix math.
    private static final float pitchMax = 89;
    private static final float pitchMin = -89;

    // Width and height of the viewport this camera is responsible for
    private int width;
    private int height;

    // Framebuffer dimensions for making the viewport work on high DPI screens
    private int framebufferWidth;
    private int framebufferHeight;

    public Camera(Window win, float myFov) {
        window = win;
        fov = myFov;

        width = window.getWidth();
        height = window.getHeight();

        framebufferWidth = window.getFramebufferWidth();
        framebufferHeight = window.getFramebufferHeight();
    }

    public void calculate() {
        // calculate top and bottom planes of the frustum, top is half the height
        // of the near plane (vertical fov) and bottom is the same but negative
        float nearHalf = (float) Math.tan(Math.toRadians(fov) / 2.0);
        float top = near * nearHalf;
        float bottom = -top;

        // calculate aspect ratio
        float aspect = (float) width / height;

        // right and left planes calculated basically the same way but accounting
        // for the dimensions of the viewport
        float right = top * aspect;
        float left = -right;

        // build the projection matrix
        glMatrixMode(GL_PROJECTION);

        // Reset the projection matrix to the default
        glLoadIdentity();

        // Set the viewport (how much of the window will we use)
        glViewport(0, 0, framebufferWidth, framebufferHeight);

        // Create the perspective projection
        glFrustum(left, right, bottom, top, near, far);

        // time to apply camera transformations and rotation!!!!!
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // rotate first so that we rotate around the camera's position and not
        // a world coordinate (caused by moving first. this is backwards and stupid)
        glRotatef(-pitch, 1, 0, 0);
        glRotatef(yaw, 0, 1, 0);

        // translate the opposite of the camera position. this is because opengl does
        // not have a camera, and the way the "camera" is moved is by moving the world
        glTranslatef(-x, -y, -z);
    }

    public Vector3 getDirection(float yawOffset, float pitchOffset) {
        // converts the pitch and yaw of this camera into a vector that represents
        // the direction the camera is facing and normalizes it so that it does not scale
        // if it is used for movement
        Vector3 forward = new Vector3();

        // Convert yaw and pitch (stored in degrees as floats) to radians, because Math trig
        // functions operate in radians. We have pitch and yaw offsets so that callers of this
        // method may negate one of those axes (for example, only getting the forward direction
        // horizontally) and subtract 90 because it normally gives the direction to the right.
        float yawRad = (float)Math.toRadians(yaw - (90 + yawOffset));
        float pitchRad = (float)Math.toRadians(pitch - pitchOffset);

        // Do trig to convert the yaw and pitch to a 3d vector
        forward.x = (float)(Math.cos(yawRad) * Math.cos(pitchRad));
        forward.y = (float)Math.sin(pitchRad);
        forward.z = (float)(Math.sin(yawRad) * Math.cos(pitchRad));

        // Normalize this vector so that it has a magnitude of 1 to make sure that using it
        // to move something does not cause unintended behavior
        forward.normalize();
        return forward;
    }

    public Vector3 getDirection() {
        return getDirection(0, 0);
    }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setPosition(Vector3 pos) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public void translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void translate(Vector3 amount) {
        translate(amount.x, amount.y, amount.z);
    }

    public void setAngle(float y, float p) {
        yaw = y;
        pitch = Math.clamp(p, Camera.pitchMin, Camera.pitchMax);
    }

    public void rotate(float y, float p) {
        this.yaw += y;
        this.pitch += p;
        pitch = Math.clamp(pitch, Camera.pitchMin, Camera.pitchMax);
    }

    public Vector3 getPosition() {
        return new Vector3(x, y, z);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
