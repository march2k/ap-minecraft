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

    public Block getBlock(int x, int y, int z) {
        // Thomas making a few edits here to stop ArrayIndexOutOfBounds
        int _x = Math.clamp(x, 0, maxX-1);
        int _y = Math.clamp(y, 0, maxY-1);
        int _z = Math.clamp(z, 0, maxZ-1);

        return world[_x][_y][_z];
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
                        b.draw();
                    }
                }
            }
        }
    }
}
