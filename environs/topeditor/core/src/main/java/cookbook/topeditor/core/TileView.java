package cookbook.topeditor.core;

import pythagoras.i.Point;

import playn.core.*;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class TileView {

  private final TileSheet _sheet;
  private final Camera _camera;
  private final TileStore _store;
  private final int _width, _height;
  private final int[] _tiles;
  private final CanvasImage _blank;

  private boolean _dirty;
  private float _nextFlush;

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
          surf.drawImage(tileIdx < 0 ? _blank : _sheet.tile(tileIdx), rx, ry);
        }
      }
    }
  });

  public TileView (TileSheet sheet, Camera camera, TileStore store, int width, int height) {
    _sheet = sheet;
    _camera = camera;
    _store = store;
    _width = width;
    _height = height;
    _tiles = new int[width*height];
    for (int ii = 0; ii < _tiles.length; ii++) _tiles[ii] = -1;
    _blank = graphics().createImage(_sheet.tileWidth, _sheet.tileHeight);
    _blank.canvas().setStrokeColor(0xFFFFFFFF).strokeRect(
      1, 1, _sheet.tileWidth-2, _sheet.tileHeight-2);
  }

  public void init () {
    // load our tile data from storage
    _store.load(_tiles);
    // initialize the camera with our view size
    _camera.setViewSize(_width * _sheet.tileWidth, _height * _sheet.tileHeight);
    // add our tile view layer to the scene graph
    graphics().rootLayer().add(layer);
  }

  public void getTile (float x, float y, Point into) {
    into.x = (int)(x + _camera.x()) / _sheet.tileWidth;
    into.y = (int)(y + _camera.y()) / _sheet.tileHeight;
  }

  public void setTile (int tx, int ty, int tileId) {
    _tiles[ty * _width + tx] = tileId;
    _dirty = true;
  }

  public void dumpTiles () {
    _store.dump(_tiles);
  }

  public void update (Clock clock) {
    if (_dirty && clock.time() > _nextFlush) {
      _dirty = false;
      _nextFlush = clock.time() + 1000; // don't flush more than once per second
      _store.store(_tiles);
    }
  }
}
