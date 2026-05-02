import java.util.ArrayList;

public class Player {
    private Camera camera;
    private World world;

    private Vector3 position;
    private Vector3 velocity;

    private CollisionBody body;
    private Vector3 size;

    private static final float buffer = 0.01f;
    private static final float gravity = -0.001f;
    private static final float friction = 0.92f;
    private boolean onGround;

    public Player(Camera cam, World w, Vector3 initialPosition) {
        camera = cam;
        world = w;

        position = new Vector3(initialPosition);
        velocity = new Vector3();
        size = new Vector3(0.75f, 1.75f, 0.75f);

        body = new CollisionBody();
        updateBody();
    }

    public void update() {
        camera.setPosition(new Vector3(position.x, position.y + size.y, position.z));

        // create a CollisionBody to use for collisions
        updateBody();

        // Try to move and collide
        move();

        // Apply gravity if we are in the air
        if(!onGround) {
            velocity.y -= 0.001f;
        }

        velocity.x *= friction;
        velocity.z *= friction;
    }

    private void move() {
        ArrayList<CollisionBody> bodies = getBlockBodies();

        position.x += velocity.x;
        updateBody();

        for (CollisionBody b : bodies) {
            if (b.intersects(this.body)) {
                System.out.println("X collision @ " + position.x + " velocity: " + velocity.x);
                if (velocity.x > 0) {
                    position.x = b.min.x - (size.x / 2) - 0.001f;
                } else if (velocity.x < 0) {
                    position.x = b.max.x + (size.x / 2) + 0.001f;
                }
                System.out.println("New X coordinate: " + position.x);
                velocity.x = 0;
                updateBody();
            }
        }

        position.y += velocity.y;
        updateBody();

        for(CollisionBody b: bodies) {
            if(b.intersects(body)) {
                System.out.println("Y collision @ " + position.y + " velocity: " + velocity.y);
                if(velocity.y > 0) {
                    position.y = b.min.y - size.y;
                } else if(velocity.y < 0) {
                    position.y = b.max.y;
                }
                System.out.println("New Y coordinate: " + position.y);
                velocity.y = 0;
                updateBody();
            }
        }

        position.z += velocity.z;
        updateBody();

        for (CollisionBody b : bodies) {
            if (b.intersects(this.body)) {
                System.out.println("Z collision @ " + position.z + " velocity: " + velocity.z);
                if (velocity.z > 0) {
                    position.z = b.min.z - (size.z / 2) - 0.001f;
                } else if (velocity.z < 0) {
                    position.z = b.max.z + (size.z / 2) + 0.001f;
                }
                System.out.println("New Z coordinate: " + position.z);
                velocity.z = 0;
                updateBody();
            }
        }

        onGround = checkOnGround(bodies);
    }

    private boolean checkOnGround(ArrayList<CollisionBody> bodies) {
        updateBody();
        CollisionBody tester = new CollisionBody(body);
        tester.min.y -= buffer;

        for(CollisionBody b: bodies) {
            if(b.intersects(tester)) {
                return true;
            }
        }

        return false;
    }

    private ArrayList<CollisionBody> getBlockBodies() {
        ArrayList<CollisionBody> bodies = new ArrayList<>();
        int x = (int)Math.ceil(position.x);
        int y = (int)Math.ceil(position.y);
        int z = (int)Math.ceil(position.z);

        for(int addX = x - 2; addX <= x + 2; addX++) {
            for(int addY = y - 1; addY <= y + 2; addY++) {
                for(int addZ = z - 2; addZ <= z + 2; addZ++) {
                    bodies.add(world.getBodyForBlock(addX, addY, addZ));
                }
            }
        }

        return bodies;
    }

    private void updateBody() {
        /*body.min.x = position.x - (size.x / 2);
        body.min.y = position.y;
        body.min.z = position.z - (size.z / 2);
        body.max.x = position.x + (size.x / 2);
        body.max.y = position.y + size.y;
        body.max.z = position.z + (size.z / 2);*/

        float halfWidth = size.x / 2;
        float halfDepth = size.z / 2;
        body.min = new Vector3(position.x - halfWidth, position.y, position.z - halfDepth);
        body.max = new Vector3(position.x + halfWidth, position.y + size.y, position.z + halfDepth);
    }

    public void accelerate(Vector3 direction) {
        velocity.add(direction);
    }
}
