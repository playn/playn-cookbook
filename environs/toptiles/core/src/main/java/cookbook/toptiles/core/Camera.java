package cookbook.toptiles.core;

import pythagoras.f.MathUtil;

import playn.core.Key;
import playn.core.util.Clock;
import static playn.core.PlayN.graphics;

public class Camera {

  private final float CAM_VEL = 0.5f; // pixels per millisecond
  private float _maxX, _maxY;         // bounds the camera to the view size
  private float _dx, _dy;             // our current scroll directions
  private float _x, _y;               // the current camera position

  public int x () { return (int)_x; }
  public int y () { return (int)_y; }

  public void setViewSize (int width, int height) {
    _maxX = width - graphics().width();
    _maxY = height - graphics().height();
  }

  public void paint (Clock clock) {
    _x = MathUtil.clamp(_x + CAM_VEL * _dx * clock.dt(), 0, _maxX);
    _y = MathUtil.clamp(_y + CAM_VEL * _dy * clock.dt(), 0, _maxY);
  }

  public boolean onKey (Key key, boolean down) {
    int vel = down ? 1 : 0;
    switch (key) {
    case  LEFT: _dx = -vel; return true;
    case RIGHT: _dx =  vel; return true;
    case    UP: _dy = -vel; return true;
    case  DOWN: _dy =  vel; return true;
    default: return false;
    }
  }
}
