package cookbook.dungeon.core;

public class Dungeon {

  public final int width, height;
  public final boolean[] tiles;

  public Dungeon (int width, int height) {
    this.width = width;
    this.height = height;
    this.tiles = new boolean[width*height];

    // generate a simple example dungeon (assumes 24x12 size)
    excavate(1, 1, 3, 3);
    excavate(4, 3, 5, 1);
    excavate(8, 4, 8, 4);
    excavate(12, 1, 9, 1);
    excavate(12, 1, 1, 3);
    excavate(20, 1, 1, 2);
    excavate(2, 4, 1, 6);
    excavate(0, 10, 7, 2);
    excavate(7, 11, 12, 1);
    excavate(19, 9, 3, 3);
    excavate(18, 3, 6, 3);
  }

  public boolean tile (int x, int y) {
    return (x < 0 || x >= width || y < 0 || y >= height) ? false : tiles[y*width+x];
  }

  protected void excavate (int x, int y, int width, int height) {
    for (int yy = y, my = y+height; yy < my; yy++) {
      int row = yy*this.width;
      for (int xx = x, mx = x+width; xx < mx; xx++) tiles[row+xx] = true;
    }
  }
}
