/*
Vector3.java - Thomas

The Vector3 class is basically a math helper, it is used primarily for calculating the
direction vector of the camera, storing it, and scaling it. It basically just has some
helping methods like vector normalization
 */

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

    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
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

    public Vector3 add(Vector3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3 set(Vector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
        return this;
    }

    public boolean equals(Vector3 other) {
        if(x != other.x) {
            return false;
        }
        if(y != other.y) {
            return false;
        }
        if(z != other.z) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
