import static org.lwjgl.opengl.GL11.*;

public class Block {
    private Texture side;
    private Texture top;

    public Block(String sidePath, String topPath) {
        side = new Texture(sidePath);
        top = new Texture(topPath);
    }

    public void draw() {
        glPushMatrix();

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
        glPopMatrix();
    }
}
