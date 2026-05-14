public class RayIntersect {
    public Vector3 pClose, pFar;
    public float close, far;

    public RayIntersect() {
        pClose = new Vector3();
        pFar = new Vector3();
    }

    public String toString() {
        return "pclose: " + pClose + " # pfar: " + pFar + " # close: " + close + " # far: " + far;
    }
}
