package cookbook.topeditor.core;

import pythagoras.i.Point;

import playn.core.*;

public class TilePainter extends Pointer.Adapter {

  private final TileView _view;
  private final TileChooser _chooser;
  private final Point _start = new Point(), _cur = new Point(), _last = new Point();
  private boolean _rectMode;

  public TilePainter (TileView view, TileChooser chooser) {
    _view = view;
    _chooser = chooser;
    _view.layer.addListener(this);
    // an immediate layer doesn't normally have bounds (and therefore doesn't normally get clicks),
    // so we need a custom hit tester to say "yes, we intercept all clicks that didn't get
    // intercepted by a layer above us"
    _view.layer.setHitTester(new Layer.HitTester() {
      public Layer hitTest (Layer layer, pythagoras.f.Point p) { return _view.layer; }
    });
  }

  public boolean onKey (Key key, boolean down) {
    switch (key) {
    case R: _rectMode = down; return true;
    default: return false;
    }
  }

  @Override public void onPointerStart (Pointer.Event event) {
    _view.getTile(event.x(), event.y(), _start);
    _last.setLocation(-1, -1);
    onPointerDrag(event);
  }

  @Override public void onPointerDrag (Pointer.Event event) {
    _view.getTile(event.x(), event.y(), _cur);
    if (_cur.equals(_last)) return; // stop if we're still on the same tile
    _last.setLocation(_cur);

    if (!_rectMode) _view.setTile(_cur.x, _cur.y, _chooser.selTileIdx);
    else {
      int sx = Math.min(_start.x, _cur.x), ex = Math.max(_start.x, _cur.x);
      int sy = Math.min(_start.y, _cur.y), ey = Math.max(_start.y, _cur.y);
      for (int yy = sy; yy <= ey; yy++) {
        for (int xx = sx; xx <= ex; xx++) {
          _view.setTile(xx, yy, _chooser.selTileIdx);
        }
      }
    }
  }
}
