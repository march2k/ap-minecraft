public class Ray {
    public Vector3 origin;
    public Vector3 direction;

    public Ray() {
        this.origin = new Vector3();
        this.direction = new Vector3();
    }

    public Ray(Vector3 o, Vector3 dir) {
        this.origin = new Vector3(o);
        this.direction = new Vector3(dir);
    }

    // Implement a ray to AABB collision check using the slab method
    public RayIntersect intersects(CollisionBody other) {
        Vector3 vLow = new Vector3();
        Vector3 vHigh = new Vector3();

        vLow.x = (other.min.x - origin.x) / direction.x;
        vLow.y = (other.min.y - origin.y) / direction.y;
        vLow.z = (other.min.z - origin.z) / direction.z;

        vHigh.x = (other.max.x - origin.x) / direction.x;
        vHigh.y = (other.max.y - origin.y) / direction.y;
        vHigh.z = (other.max.z - origin.z) / direction.z;

        Vector3 vClose = new Vector3();
        Vector3 vFar = new Vector3();

        vClose.x = Math.min(vLow.x, vHigh.x);
        vClose.y = Math.min(vLow.y, vHigh.y);
        vClose.z = Math.min(vLow.z, vHigh.z);

        vFar.x = Math.max(vLow.x, vHigh.x);
        vFar.y = Math.max(vLow.y, vHigh.y);
        vFar.z = Math.max(vLow.z, vHigh.z);

        float close = Math.max(Math.max(vClose.x, vClose.y), vClose.z);
        float far = Math.min(Math.min(vFar.x, vFar.y), vFar.z);

        RayIntersect intersect = new RayIntersect();

        intersect.pClose.x = origin.x + (vClose.x + direction.x);
        intersect.pClose.y = origin.y + (vClose.y + direction.y);
        intersect.pClose.z = origin.z + (vClose.z + direction.z);

        intersect.pFar.x = origin.x + (vFar.x + direction.x);
        intersect.pFar.y = origin.y + (vFar.y + direction.y);
        intersect.pFar.z = origin.z + (vFar.z + direction.z);

        intersect.close = close;
        intersect.far = far;

        return intersect;
    }
}
