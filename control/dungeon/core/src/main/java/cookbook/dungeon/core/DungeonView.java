package cookbook.dungeon.core;

import playn.core.*;
import pythagoras.f.MathUtil;
import static playn.core.PlayN.*;

public class DungeonView {

  public final static int TWID = 40, THEI = 40; // the width/height of a tile, in pixels
  public final Dungeon dungeon;
  public float camX, camY; // our current camera position (based on center of view)

  public final Layer layer = graphics().createImmediateLayer(new ImmediateLayer.Renderer() {
    public void render (Surface surf) {
      // start with a black background
      float width = graphics().width(), height = graphics().height();
      surf.setFillColor(0xFF000000).fillRect(0, 0, width, height);
      // determine our minX and minY based on the top-left of the screen
      int left = Math.round(camX - width/2), top = Math.round(camY - height/2);
      int minX = left / TWID, offX = left % TWID;
      int minY = top / THEI, offY = top % THEI;
      // draw our grey dungeon floor for all walkable tiles in view
      surf.setFillColor(0xFF999999);
      for (int yy = minY, ry = -offY; ry < height; yy++, ry += THEI) {
        for (int xx = minX, rx = -offX; rx < width; xx++, rx += THEI) {
          if (dungeon.tile(xx, yy)) surf.fillRect(rx, ry, TWID-1, THEI-1);
        }
      }
    }
  });

  public DungeonView (Dungeon dungeon) {
    this.dungeon = dungeon;
  }

  public void init () {
    // add our view layer to the scene graph
    graphics().rootLayer().add(layer);
  }

  public int toTileX (float x) { return MathUtil.ifloor(x / TWID); }
  public int toTileY (float y) { return MathUtil.ifloor(y / THEI); }
}
