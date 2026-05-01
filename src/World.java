public class World
{
    Block[][][] world;
    private int maxX, maxY, maxZ;

    public World(int x, int y, int z)
    {
        maxX = x;
        maxY = y;
        maxZ = z;
        world = new Block[x][y][z];
        generate();
    }

    public void draw()
    {
        for(int x = 0; x < maxX; x++)
        {
            for(int y = 0; y < maxY; y++)
            {
                for(int z = 0; z < maxZ; z++)
                {
                    Block b = world[x][y][z];
                    if(b != null)
                    {
                        b.draw(x, y, z);
                    }
                }
            }
        }
    }

    private void generate()
    {
        for(int x = 0; x < maxX; x++)
        {
            for(int z = 0; z < maxZ; z++)
            {
                world[x][0][z] = new Block("/cobblestone.png", "/cobblestone.png");
                world[x][1][z] = new Block("/dirt.png", "/grass.png");
            }
        }
    }
}
