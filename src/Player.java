import java.util.ArrayList;

public class Player {
    // The camera that this player will occupy
    private Camera camera;

    // The world that this player is in
    private World world;

    // The CollisionBody responsible for this player's collisions
    private CollisionBody body;

    // The CollisionBody responsible for checking X and Z collisions
    // I have this because I do not want floors or ceilings considered when doing those checks
    private CollisionBody smaller;

    // Player CollisionBody measurements
    private static final float width = 0.75f;
    private static final float height = 1.75f;
    private static final float depth = 0.75f;

    // Amount to buffer collision checks by (reduce test hitbox by this much in order to
    // avoid testing collision with unwanted walls, ceilings, etc)
    private static final float buffer = 0.1f;

    // Position and velocity of this player
    private Vector3 position;
    private Vector3 velocity;

    // Physical properties of the player
    private boolean onGround;
    private static final float friction = 0.92f;
    private static final float gravity = 0.001f;

    public Player(World world, Camera cam, Vector3 spawnLocation) {
        // Spawn in the player at the requested position and initialize velocity
        setPosition(spawnLocation);
        velocity = new Vector3();

        // Keep our camera and world for use later
        this.camera = cam;
        this.world = world;

        // Create our player's CollisionBody
        body = new CollisionBody(position, new Vector3(width, height, depth));
    }

    public void update() {
        // Slave the camera to this player at head level
        camera.setPosition(new Vector3(position).add(new Vector3(0.5f, height, 0.5f)));

        // Rebuild the collision body for the current position since it might have moved
        recalculateCollisionBody();

        // Try to move based on the current velocity
        move();

        // Apply gravity
        if(!onGround) {
            velocity.y -= gravity;
        }

        // Apply friction
        if(onGround) {

        }
        velocity.x *= friction;
        velocity.z *= friction;

        System.out.println(getPosition() + " onGround: " + onGround);
    }

    private void move() {
        ArrayList<CollisionBody> nearby = getNearbyBlockCollisionBodies();

        position.x += velocity.x;
        recalculateCollisionBody();
        for(CollisionBody b: nearby) {
            if(smaller.intersects(b)) {
                System.out.println("X collided with block: " + velocity.x);
                if(velocity.x > 0) {
                    position.x = b.getMin().x - width - buffer;
                } else if(velocity.x < 0) {
                    position.x = b.getMax().x;
                }
                velocity.x = 0;
            }
        }

        position.y += velocity.y;
        recalculateCollisionBody();
        for(CollisionBody b: nearby) {
            if(body.intersects(b)) {
                if(velocity.y > 0) {
                    position.y = b.getMin().y - height;
                } else if(velocity.y < 0) {
                    position.y = b.getMax().y;
                }
                velocity.y = 0;
            }
        }

        position.z += velocity.z;
        recalculateCollisionBody();
        for(CollisionBody b: nearby) {
            if(smaller.intersects(b)) {
                System.out.println("Z collided with block: " + velocity.z);
                if(velocity.z > 0) {
                    position.z = b.getMin().z - depth - buffer;
                } else if(velocity.z < 0) {
                    position.z = b.getMax().z;
                }
                velocity.z = 0;
            }
        }

        recalculateCollisionBody();
        onGround = checkOnGround(nearby);
    }

    private boolean checkOnGround(ArrayList<CollisionBody> nearby) {
        CollisionBody tester = new CollisionBody(new Vector3(position.x, position.y - buffer, position.z),
                new Vector3(width - buffer, height, depth - buffer));

        for(CollisionBody b: nearby) {
            if(tester.intersects(b)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<CollisionBody> getNearbyBlockCollisionBodies() {
        ArrayList<CollisionBody> bodies = new ArrayList<>();

        int x = (int)Math.ceil(position.x);
        int y = (int)Math.ceil(position.y);
        int z = (int)Math.ceil(position.z);

        for(int addY = y-1; addY <= x+2; addY++) {
            for(int addX = y-1; addX <= x+2; addX++) {
                for(int addZ = y-1; addZ <= x+2; addZ++) {
                    bodies.add(getBodyAt(addX, addY, addZ));
                }
            }
        }

        return bodies;
    }

    private void recalculateCollisionBody() {
        body = new CollisionBody(new Vector3(position).add(new Vector3(width / 2.0f, 0, depth / 2.0f)),
                new Vector3(width, height, depth));

        // make this box smaller to avoid checking against floors and ceilings
        smaller = new CollisionBody(new Vector3(position.x, position.y + buffer, position.z),
                new Vector3(width, height - (buffer * 2), depth));
    }

    private CollisionBody getBodyAt(float x, float y, float z) {
        Block block = world.getBlock((int)x, (int)y, (int)z);
        if(block != null) {
            return block.createBody();
        }
        return new CollisionBody(new Vector3(), new Vector3());
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void accelerate(Vector3 direction) {
        velocity.add(direction);
    }

    public void setPosition(Vector3 pos) {
        position = new Vector3(pos);
    }

    public void resetVelocity() {
        velocity = new Vector3();
    }

    public Vector3 getPosition() {
        return position;
    }
}
