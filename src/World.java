public class World
{
    Block[][][] world;
    private int maxX, maxY, maxZ;
    private WorldGenerator worldGenerator;

    public World(WorldGenerator gen, int x, int y, int z)
    {
        maxX = x;
        maxY = y;
        maxZ = z;
        world = new Block[x][y][z];
        worldGenerator = gen;
        worldGenerator.generate(world, maxX, maxY, maxZ);
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
}
