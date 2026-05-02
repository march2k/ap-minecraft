public class WorldGeneratorFlat implements WorldGenerator
{

    @Override
    public void generate(Block[][][] world, int maxX, int maxY, int maxZ)
    {
        for(int x = 0; x < maxX; x++)
        {
            for(int z = 0; z < maxZ; z++)
            {
                world[x][0][z] = new Block(new Vector3(x, 0, z),
                        "/cobblestone.png", "/cobblestone.png");
                world[x][1][z] = new Block(new Vector3(x, 1, z),
                        "/dirt.png", "/grass.png");
            }
        }

        // Adding a test wall
        world[2][2][2] = new Block(new Vector3(2, 2,2),
                "/dirt.png", "/grass.png");
    }
}
