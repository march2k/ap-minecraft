import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture
{
    private int width;
    private int height;
    private int handle;

    public Texture(String path)
    {
        stbi_set_flip_vertically_on_load(true);
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer channelBuffer = BufferUtils.createIntBuffer(1);
        ByteBuffer image;
        try {
            image = loadResource(path);
        }catch(IOException e)
        {
            System.out.println("error loading texture");
            return;
        }
        ByteBuffer data = stbi_load_from_memory(image, widthBuffer, heightBuffer, channelBuffer, 4);

        width = widthBuffer.get();
        height = heightBuffer.get();

        handle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, handle);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
    }

    public static ByteBuffer loadResource(String path) throws IOException {
        InputStream i = Texture.class.getResourceAsStream(path);
        byte[] bytes = i.readAllBytes();
        ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
        buffer.put(bytes);
        buffer.flip(); // allows bytes to be read at same order put in
        return buffer;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void use()
    {
        glBindTexture(GL_TEXTURE_2D, handle);
    }

}
