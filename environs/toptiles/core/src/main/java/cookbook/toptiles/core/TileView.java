package cookbook.toptiles.core;

import pythagoras.i.Point;

import playn.core.*;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class TileView {

  private final TileSheet _sheet;
  private final Camera _camera;
  private final int _width, _height;
  private final int[] _tiles;

  public final ImmediateLayer layer = graphics().createImmediateLayer(new ImmediateLayer.Renderer() {
    public void render (Surface surf) {
      // start with a black background
      float width = graphics().width(), height = graphics().height();
      surf.setFillColor(0xFF000000).fillRect(0, 0, width, height);
      // determine our minX and minY based on the camera position
      int camX = _camera.x(), camY = _camera.y();
      int minX = camX / _sheet.tileWidth,  offX = camX % _sheet.tileWidth;
      int minY = camY / _sheet.tileHeight, offY = camY % _sheet.tileHeight;
      // now render our tiles
      for (int yy = minY, ry = -offY; ry < height; yy++, ry += _sheet.tileHeight) {
        int off = _width*yy;
        for (int xx = minX, rx = -offX; rx < width; xx++, rx += _sheet.tileWidth) {
          int tileIdx = _tiles[off + xx];
          if (tileIdx >= 0) surf.drawImage(_sheet.tile(tileIdx), rx, ry);
        }
      }
    }
  });

  public TileView (TileSheet sheet, Camera camera, int width, int height) {
    _sheet = sheet;
    _camera = camera;
    _width = width;
    _height = height;
    _tiles = new int[width*height];
    for (int ii = 0; ii < _tiles.length; ii++) _tiles[ii] = -1;
  }

  public void init (int[] tiles) {
    // apply our saved tiles
    System.arraycopy(tiles, 0, _tiles, 0, Math.min(tiles.length, _tiles.length));
    // initialize the camera with our view size
    _camera.setViewSize(_width * _sheet.tileWidth, _height * _sheet.tileHeight);
    // add our tile view layer to the scene graph
    graphics().rootLayer().add(layer);
  }
}
