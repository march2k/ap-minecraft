import static org.lwjgl.opengl.GL11.*;

public class TestTriangle {
    private float rot;

    public void draw() {
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();

        rot++;
        glRotatef(rot, 0, 1, 0);
        glRotatef(rot * 2, 1, 0, 0);

        glBegin(GL_TRIANGLES);

        glVertex3f(0, 0, 1);
        glVertex3f(0, 1, 0);
        glVertex3f(2, 1, 0);

        glEnd();
        glPopMatrix();
    }
}
