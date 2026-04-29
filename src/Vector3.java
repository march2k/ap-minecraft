public class Vector3 {
    public float x, y, z;

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 normalize() {
        float length = (float)Math.sqrt((x*x) + (y*y) + (z*z));

        if(length > 0) {
            x /= length;
            y /= length;
            z /= length;
        }

        return this;
    }

    public Vector3 scl(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
