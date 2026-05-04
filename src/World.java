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
        worldGenerator.generate(this, maxX, maxY, maxZ);
    }

    public Block getBlock(int x, int y, int z) {
        // Thomas making a few edits here to stop ArrayIndexOutOfBounds
        int _x = Math.clamp(x, 0, maxX-1);
        int _y = Math.clamp(y, 0, maxY-1);
        int _z = Math.clamp(z, 0, maxZ-1);

        return world[_x][_y][_z];
    }

    public CollisionBody getBodyForBlock(int x, int y, int z) {
        Block block = getBlock(x, y, z);
        if(block != null) {
            return block.createBody();
        }
        return new CollisionBody();
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

    public int getMaxX()
    {
        return maxX;
    }

    public int getMaxY()
    {
        return maxY;
    }

    public int getMaxZ()
    {
        return maxZ;
    }

    public void set(int x, int y, int z, String side, String top)
    {
        world[x][y][z] = new Block(new Vector3(x, y, z), side, top);
    }
}
