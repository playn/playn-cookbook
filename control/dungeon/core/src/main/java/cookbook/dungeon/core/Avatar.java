package cookbook.dungeon.core;

import playn.core.*;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class Avatar {

  private final Sprite _sprite = new Sprite(assets().getImage("images/avatar.png"), 32, 36, 3, 100);
  private final DungeonView _view;

  private final float WALK_VEL = 0.3f; // pixels per millisecond
  private float _dx, _dy;              // our current scroll directions
  private float _x, _y;                // the current avatar (pixel) position

  public Avatar (DungeonView view) {
    _view = view;
  }

  public void init () {
    // add our sprite layer to the scene graph
    graphics().rootLayer().addAt(_sprite.layer, graphics().width()/2, graphics().height()/2);
    // start in the center of the world
    move(_view.dungeon.width*DungeonView.TWID/2, _view.dungeon.height*DungeonView.THEI/2);
  }

  public boolean onKey (Key key, boolean down) {
    switch (key) {
    case  LEFT: _dx = (down ? -1 : 0); return true;
    case RIGHT: _dx = (down ?  1 : 0); return true;
    case    UP: _dy = (down ? -1 : 0); return true;
    case  DOWN: _dy = (down ?  1 : 0); return true;
    default: return false;
    }
  }

  public void paint (Clock clock) {
    // if we're not moving, just put our sprite in idle mode
    if (_dx == 0 && _dy == 0) {
      _sprite.idle();
      return;
    }

    float nx = _x + WALK_VEL * _dx * clock.dt();
    float ny = _y + WALK_VEL * _dy * clock.dt();
    float ahwid = _sprite.width/2f, ahhei = _sprite.height/2f;

    // set the sprite's orientation based on our movement direction
    if (_dy < 0) _sprite.setRow(0);
    else if (_dx < 0) _sprite.setRow(3);
    else if (_dx > 0) _sprite.setRow(1);
    else if (_dy > 0)  _sprite.setRow(2);

    // update our sprite's animation
    _sprite.paint(clock);

    // check whether any of our four corners collide with a wall
    float top = ny-ahhei, bot = ny+ahhei, left = nx-ahwid, right = nx+ahwid;
    int ttop = _view.toTileY(top), tbot = _view.toTileY(bot);
    int tleft = _view.toTileX(left), tright = _view.toTileX(right);
    if (_view.dungeon.tile(tleft, ttop) &&
        _view.dungeon.tile(tright, ttop) &&
        _view.dungeon.tile(tleft, tbot) &&
        _view.dungeon.tile(tright, tbot)) move(nx, ny);
  }

  private void move (float x, float y) {
    _x = x;
    _y = y;
    _view.camX = x;
    _view.camY = y;
  }
}
