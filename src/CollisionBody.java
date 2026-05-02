import static org.lwjgl.opengl.GL11.*;

public class CollisionBody {
    private Vector3 min, max;

    public CollisionBody(Vector3 pos, Vector3 size) {
        min = new Vector3(pos);
        max = new Vector3(pos).add(size);
    }

    public boolean intersects(CollisionBody other) {
        return min.x < other.max.x && max.x > other.min.x &&
                min.y < other.max.y && max.y > other.min.y &&
                min.z < other.max.z && max.z > other.min.z;
    }

    public void setPosition(Vector3 pos) {
        Vector3 size = new Vector3(max.x - min.x, max.y - min.y, max.z - min.z);
        min = new Vector3(pos);
        max = new Vector3(pos).add(size);
    }

    public Vector3 getMin() {
        return min;
    }

    public Vector3 getMax() {
        return max;
    }
}
