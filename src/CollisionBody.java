import static org.lwjgl.opengl.GL11.*;

public class CollisionBody {
    public Vector3 min;
    public Vector3 max;

    public CollisionBody(Vector3 n, Vector3 x) {
        min = new Vector3(n);
        max = new Vector3(x);
    }

    public CollisionBody(CollisionBody other) {
        min = new Vector3(other.min);
        max = new Vector3(other.max);
    }

    public CollisionBody() {
        min = new Vector3();
        max = new Vector3();
    }

    // Test if another CollisionBody intersects this one
    public boolean intersects(CollisionBody other) {
        return min.x < other.max.x && max.x > other.min.x &&
                min.y < other.max.y && max.y > other.min.y &&
                min.z < other.max.z && max.z > other.min.z;
    }

    public String toString() {
        String s = new String();
        s += "min: " + min + "\n";
        s += "max: " + max + "\n";
        return s;
    }
}
