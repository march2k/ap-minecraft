import static org.lwjgl.opengl.GL11.*;

public class TestTriangle {
    private float rot;
    private Texture texture;

    public TestTriangle()
    {
        texture = new Texture("/tuffturtle.png");
    }

    public void draw() {
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();

        //rot += 0.1;
        glRotatef(rot, 0, 1, 0);
        glRotatef(rot * 2, 1, 0, 0);

        texture.use();
        glBegin(GL_TRIANGLES);

        glTexCoord2f(0,0);
        glVertex3f(0, 0, 1);
        glTexCoord2f(0,1);
        glVertex3f(0, 1, 0);
        glTexCoord2f(1,1);
        glVertex3f(2, 1, 0);

        glEnd();
        glPopMatrix();
    }
}
