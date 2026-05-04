public class WorldGeneratorFlat implements WorldGenerator
{

    @Override
    public void generate(World world, int maxX, int maxY, int maxZ)
    {
        for(int x = 0; x < maxX; x++)
        {
            for(int z = 0; z < maxZ; z++)
            {
                world.set(x,0, z, "/cobblestone.png", "/cobblestone.png");
                world.set(x, 1, z, "/dirt.png", "/grass.png");
                /*world[x][0][z] = new Block(new Vector3(x, 0, z),
                        "/cobblestone.png", "/cobblestone.png");
                world[x][1][z] = new Block(new Vector3(x, 1, z),
                        "/dirt.png", "/top.png");

                 */
            }
        }

        // Adding a test wall
        world.set(3,2,3, "/dirt.png", "/grass.png");
        world.set(3,2,4, "/dirt.png", "/grass.png");
        world.set(3,3,4, "/dirt.png", "/grass.png");
        /*world[3][2][3] = new Block(new Vector3(3, 2,3),
                "/dirt.png", "/grass.png");
        world[3][2][4] = new Block(new Vector3(3, 2,4),
                "/dirt.png", "/grass.png");
        world[3][3][4] = new Block(new Vector3(3, 3,4),
                "/dirt.png", "/grass.png");

         */
    }
}
