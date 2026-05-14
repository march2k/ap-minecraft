import java.util.ArrayList;

public class Player {
    // Camera for us to see the world through
    private Camera camera;

    // World for us to get nearby blocks for collisions
    private World world;

    // Position and velocity of this player
    private Vector3 position;
    private Vector3 velocity;

    // Our player's CollisionBody and its size
    private CollisionBody body;
    private Vector3 size;

    // Picked block (the block we are looking at)
    private static final float reach = 3.0f;
    private Vector3 hitPos;
    private Block picked;

    // Constants used in collision detection and adjustments
    private static final float buffer = 0.01f;
    private static final float pixel = 0.001f;

    // Physical properties about this player
    private final float gravity = 0.2f;
    private final float friction = 0.80f;
    private final float speed = 1.0f;
    private final float jumpPower = 5.5f;
    private final float eyePos = 1.75f;

    private boolean onGround;

    public Player(Camera cam, World w, Vector3 initialPosition) {
        camera = cam;
        world = w;

        position = new Vector3(initialPosition);
        velocity = new Vector3();
        hitPos = new Vector3();

        // Set the size of the player's collision box
        size = new Vector3(0.75f, 1.9f, 0.75f);

        // Build the collision box for the first time
        body = new CollisionBody();
        updateBody();
    }

    public void update() {
        // Place the camera at the player's eye
        camera.setPosition(new Vector3(position.x, position.y + eyePos, position.z));

        // Get a list of all blocks relevant to the player, for example the blocks
        // above, below, in front of us should be in this list, because it is
        // possible that we may collide or interact with them this frame.
        ArrayList<Block> blocks = getBlocks();

        // create a CollisionBody to use for collisions
        updateBody();

        // Try to move and collide
        move(blocks);

        // Check and assign if we are on the ground or not
        onGround = checkOnGround(blocks);

        // Check the picked block
        picked = pickBlock(blocks);

        // Apply gravity if we are in the air
        if(!onGround) {
            velocity.y -= gravity * Game.getDeltaTime();
        }

        // Apply friction to slow down the player when they stop moving
        velocity.x *= friction;
        velocity.z *= friction;
    }

    private void move(ArrayList<Block> blocks) {
        // move the player based on their current velocity and update the CollisionBody
        position.y += velocity.y;
        updateBody();

        ArrayList<CollisionBody> bodies = new ArrayList<>();
        for(Block i: blocks) {
            if(i == null) {
                continue;
            }
            bodies.add(i.createBody());
        }

        // Check this newly moved collision body against all relevant blocks
        for(CollisionBody b: bodies) {
            if(b.intersects(body)) {
                // Determine what side of the block we hit based on velocity
                if(velocity.y > 0) {
                    // If velocity was positive...
                    position.y = b.min.y - size.y;
                } else if(velocity.y < 0) {
                    // If velocity was negative...
                    position.y = b.max.y;
                }

                // Set velocity to 0 now that we have been snapped to the
                // end of the block & we can't move anymore
                velocity.y = 0;

                // Finalize this axis into the CollisionBody
                updateBody();
            }
        }

        // This axis (and the Z axis) work basically the same, just one difference
        // in how they snap the player.
        position.x += velocity.x;
        updateBody();

        for (CollisionBody b : bodies) {
            if (b.intersects(this.body)) {
                if (velocity.x > 0) {
                    // Have to snap the player taking account for how the center of the
                    // hitbox is actually the position of the player.
                    position.x = b.min.x - (size.x / 2) - pixel;
                } else if (velocity.x < 0) {
                    // we add the pixel constant here because if we are right on it, it is
                    // possible that there can be floating point issues and besides I don't
                    // trust having floats being set to precise values.
                    position.x = b.max.x + (size.x / 2) + pixel;
                }
                velocity.x = 0;
                updateBody();
            }
        }

        position.z += velocity.z;
        updateBody();

        for (CollisionBody b : bodies) {
            if (b.intersects(this.body)) {
                if (velocity.z > 0) {
                    position.z = b.min.z - (size.z / 2) - pixel;
                } else if (velocity.z < 0) {
                    position.z = b.max.z + (size.z / 2) + pixel;
                }
                velocity.z = 0;
                updateBody();
            }
        }
    }

    private boolean checkOnGround(ArrayList<Block> blocks) {
        // Make sure the CollisionBody is completely up to date
        updateBody();

        // Create a new CollisionBody that is slightly moved downwards
        CollisionBody tester = new CollisionBody(body);
        tester.min.y -= buffer;

        // If this tester CollisionBody was in the floor, then we know
        // that we are on the ground, otherwise we are in the air.


        for(Block b: blocks) {
            if(b == null) {
                continue;
            }

            CollisionBody cb = b.createBody();
            if(cb.intersects(tester)) {
                return true;
            }
        }

        return false;
    }

    private ArrayList<Block> getBlocks() {
        // Get all the nearby blocks for our player's current block position and
        // return their CollisionBodies in an ArrayList. We need this to make sure
        // that we are checking all the blocks we might expect to block us.
        ArrayList<Block> bodies = new ArrayList<>();
        int x = (int)Math.ceil(position.x);
        int y = (int)Math.ceil(position.y);
        int z = (int)Math.ceil(position.z);

        int range = 4;

        for(int addX = x - range; addX <= x + range; addX++) {
            for(int addY = y - range; addY <= y + range; addY++) {
                for(int addZ = z - range; addZ <= z + range; addZ++) {
                    bodies.add(world.getBlock(addX, addY, addZ));
                }
            }
        }

        return bodies;
    }

    private Block pickBlock(ArrayList<Block> blocks) {
        Ray eye = getEyeRay();
        for(Block b: blocks) {
            // Do not try to check null blocks
            if(b == null) {
                continue;
            }

            // Get the CollisionBody for the relevant block and get
            // the RayIntersect information for it
            CollisionBody cb = b.createBody();
            RayIntersect inter = eye.intersect(cb);

            // Check if this collision is valid
            if(inter.close <= inter.far) {

                // Check that this collision of the ray would be within the reach of the player
                if(inter.close <= reach) {
                    hitPos = new Vector3(inter.pClose);
                    return b;
                }
            }
        }

        // Return null in this case since we are not intersecting any with the ray
        return null;
    }

    public Ray getEyeRay() {
        return new Ray(getEyePos(), camera.getDirection());
    }

    private void updateBody() {
        // Update the player's CollisionBody to match its current position. I suppose this method
        // would also account for size changing... but that's not going to happen.
        float halfWidth = size.x / 2;
        float halfDepth = size.z / 2;
        body.min = new Vector3(position.x - halfWidth, position.y, position.z - halfDepth);
        body.max = new Vector3(position.x + halfWidth, position.y + size.y, position.z + halfDepth);
    }

    private Vector3 getEyePos() {
        return new Vector3(position).add(new Vector3(0, eyePos, 0));
    }

    public Block getPickedBlock() {
        return picked;
    }

    public void accelerate(Vector3 direction) {
        velocity.add(direction);
    }

    public boolean isOnGround() {
        return onGround;
    }

    public Vector3 getHitPos() {
        return hitPos;
    }

    public float getGravity() {
        return gravity;
    }

    public float getFriction() {
        return friction;
    }

    public float getSpeed() {
        return speed;
    }

    public float getJumpPower() {
        return jumpPower;
    }
}
