import static org.lwjgl.opengl.GL11.*;

public class Camera {
    private Window window;
    private float fov;

    // height of near plane (how close to something can you get before clipping)
    private final float near = 0.1f;

    // height of far plane (what is the render distance)
    private final float far = 100f;

    private float x, y, z;
    private float yaw, pitch;

    private static final float pitchMax = 89;
    private static final float pitchMin = -89;

    private int width;
    private int height;

    public Camera(Window win, float myFov) {
        window = win;
        width = window.getWidth();
        height = window.getHeight();
        fov = myFov;
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

        glLoadIdentity();
        glViewport(0, 0, width, height);
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

        float yawRad = (float)Math.toRadians(yaw - (90 + yawOffset));
        float pitchRad = (float)Math.toRadians(pitch - pitchOffset);

        forward.x = (float)(Math.cos(yawRad) * Math.cos(pitchRad));
        forward.y = (float)Math.sin(pitchRad);
        forward.z = (float)(Math.sin(yawRad) * Math.cos(pitchRad));

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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
