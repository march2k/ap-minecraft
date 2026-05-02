import static org.lwjgl.opengl.GL11.*;

/*
Block.java - Thomas

The Block class represents a single block somewhere in the world. It keeps track
of how to draw itself and the textures and coordinates that it needs to do that,
as well as being responsible for placing itself in the requested coordinates.
 */

public class Block {
    // Texture used for the sides and bottom of the block
    private Texture side;

    // Texture used for only the top of the block
    private Texture top;

    // Position of this block in 3D space
    private Vector3 position;

    public Block(Vector3 pos, String sidePath, String topPath) {
        position = new Vector3(pos);
        side = new Texture(sidePath);
        top = new Texture(topPath);
    }

    // Create and return a new CollisionBody representing this block in 3D space
    public CollisionBody createBody() {
        return new CollisionBody(new Vector3(position),
                new Vector3(position.x + 1, position.y + 1, position.z + 1));
    }

    public void draw() {
        // save current matrix since we will modify it
        glPushMatrix();

        // translate to put this block into the requested position
        // Adding 0.5f to make it match CollisionBodies of the blocks
        glTranslatef(position.x + 0.5f, position.y + 0.5f, position.z + 0.5f);

        // start drawing with the side texture
        side.use();
        glBegin(GL_QUADS);

        // back face
        glTexCoord2f(0, 0);
        glVertex3f(-0.5f, -0.5f, -0.5f); // bottom left
        glTexCoord2f(1, 0);
        glVertex3f(0.5f, -0.5f, -0.5f); // bottom right
        glTexCoord2f(1, 1);
        glVertex3f(0.5f, 0.5f, -0.5f); // top right
        glTexCoord2f(0, 1);
        glVertex3f(-0.5f, 0.5f, -0.5f); // top left

        // right face
        glTexCoord2f(0, 0);
        glVertex3f(0.5f, -0.5f, -0.5f); // bottom left
        glTexCoord2f(1, 0);
        glVertex3f(0.5f, -0.5f, 0.5f); // bottom right
        glTexCoord2f(1, 1);
        glVertex3f(0.5f, 0.5f, 0.5f); // top right
        glTexCoord2f(0, 1);
        glVertex3f(0.5f, 0.5f, -0.5f); // top left

        // left face
        glTexCoord2f(0, 0);
        glVertex3f(-0.5f, -0.5f, -0.5f); // bottom left
        glTexCoord2f(1, 0);
        glVertex3f(-0.5f, -0.5f, 0.5f); // bottom right
        glTexCoord2f(1, 1);
        glVertex3f(-0.5f, 0.5f, 0.5f); // top right
        glTexCoord2f(0, 1);
        glVertex3f(-0.5f, 0.5f, -0.5f); // top left

        // front face
        glTexCoord2f(0, 0);
        glVertex3f(-0.5f, -0.5f, 0.5f); // bottom left
        glTexCoord2f(1, 0);
        glVertex3f(0.5f, -0.5f, 0.5f); // bottom right
        glTexCoord2f(1, 1);
        glVertex3f(0.5f, 0.5f, 0.5f); // top right
        glTexCoord2f(0, 1);
        glVertex3f(-0.5f, 0.5f, 0.5f); // top left

        // bottom face
        glTexCoord2f(0, 0);
        glVertex3f(-0.5f, -0.5f, -0.5f); // bottom left
        glTexCoord2f(1, 0);
        glVertex3f(0.5f, -0.5f, -0.5f); // bottom right
        glTexCoord2f(1, 1);
        glVertex3f(0.5f, -0.5f, 0.5f); // top right
        glTexCoord2f(0, 1);
        glVertex3f(-0.5f, -0.5f, 0.5f); // top left

        glEnd();

        // start drawing using the top texture
        top.use();
        glBegin(GL_QUADS);

        // top face
        glTexCoord2f(0, 0);
        glVertex3f(-0.5f, 0.5f, -0.5f); // bottom left
        glTexCoord2f(1, 0);
        glVertex3f(0.5f, 0.5f, -0.5f); // bottom right
        glTexCoord2f(1, 1);
        glVertex3f(0.5f, 0.5f, 0.5f); // top right
        glTexCoord2f(0, 1);
        glVertex3f(-0.5f, 0.5f, 0.5f); // top left

        glEnd();

        // load the previously saved matrix back over the current one
        glPopMatrix();
    }

    public Vector3 getPosition() {
        return position;
    }
}
