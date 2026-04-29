import static org.lwjgl.opengl.GL11.*;

/*
Block.java - Thomas

The Block class represents a single minecraft block somewhere in the world. It keeps track
of how to draw itself and the textures and coordinates that it needs to do that, as well
as being responsible for placing itself in the requested coordinates.
 */

public class Block {
    private Texture side;
    private Texture top;

    public Block(String sidePath, String topPath) {
        side = new Texture(sidePath);
        top = new Texture(topPath);
    }

    public void draw(float x, float y, float z) {
        // save current matrix since we will modify it
        glPushMatrix();

        // translate to put this block into the requested position
        glTranslatef(x, y, z);

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
}
