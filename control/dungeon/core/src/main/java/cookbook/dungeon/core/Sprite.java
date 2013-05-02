package cookbook.dungeon.core;

import playn.core.*;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class Sprite {

  private final Image.Region _tile;
  private final int _tilesPerRow;
  private final float _msPerFrame;

  private float _nextFrame;
  private float _rowY;
  private int _frame;

  public final int width, height;
  public final ImageLayer layer;

  public Sprite (Image image, int tileWid, int tileHei, int tilesPerRow, float msPerFrame) {
    _tile = image.subImage(0, 0, tileWid, tileHei);
    _tilesPerRow = tilesPerRow;
    _msPerFrame = msPerFrame;
    _nextFrame = msPerFrame;
    width = tileWid;
    height = tileHei;
    layer = graphics().createImageLayer(_tile);
    layer.setOrigin(tileWid/2, tileHei/2);
  }

  public void setRow (int row) {
    _rowY = row * height;
    updateImage();
  }

  public void idle () {
    _nextFrame = 0;
    if (_frame != 0) {
      _frame = 0;
      updateImage();
    }
  }

  public void paint (Clock clock) {
    _nextFrame -= clock.dt();
    int frame = _frame;
    while (_nextFrame < 0) {
      _nextFrame += _msPerFrame;
      _frame = (_frame + 1) % _tilesPerRow;
    }
    if (_frame != frame) updateImage();
  }

  private void updateImage () {
    _tile.setBounds(_frame * width, _rowY, width, height);
  }
}
