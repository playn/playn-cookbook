package cookbook.asteroids.core;

import java.util.Random;
import pythagoras.f.FloatMath;
import pythagoras.f.Point;
import pythagoras.f.Vector;

import playn.core.*;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class Asteroids extends Game.Default {

  private final Clock.Source _clock = new Clock.Source(33);
  private final ImageLayer _ship = graphics().createImageLayer();

  // these track the ship's current orientation and velocity
  private static final float ROT_VEL = 0.025f; // in radians per millisecond
  private float _orient; // in radians
  private static final float ACCEL = 0.005f; // in pixels per millisecond
  private Vector _vel = new Vector(0, 0); // in pixels per millisecond

  // these track the current state of the left, right and space keys
  private boolean _left, _right, _space;

  public Asteroids() {
    super(33); // call update every 33ms (30 times per second)
  }

  @Override
  public void init() {
    // display some twinkling "stars" in the background
    graphics().rootLayer().add(graphics().createImmediateLayer(_starRenderer));

    // create a ship image and stuff it into our ship layer
    CanvasImage ship = createShip(20, 40);
    _ship.setImage(ship).setOrigin(ship.width()/2, ship.height()/2);
    graphics().rootLayer().addAt(_ship, graphics().width()/2, graphics().height()/2);

    // listen for keypresses
    keyboard().setListener(new Keyboard.Adapter() {
      @Override public void onKeyDown (Keyboard.Event event) {
        switch (event.key()) {
        case  LEFT: _left  = true; break;
        case RIGHT: _right = true; break;
        case SPACE: _space = true; break;
        default: break;
        }
      }
      @Override public void onKeyUp (Keyboard.Event event) {
        switch (event.key()) {
        case  LEFT: _left  = false; break;
        case RIGHT: _right = false; break;
        case SPACE: _space = false; break;
        default: break;
        }
      }
    });
  }

  @Override
  public void update(int delta) {
    _clock.update(delta);

    // if the left or right key are down, activate a uniform rotational velocity
    if (_left)  _orient -= ROT_VEL;
    if (_right) _orient += ROT_VEL;

    // apply our current orientation to the ship sprite
    _ship.setRotation(_orient);

    // if the space key is down, apply uniform acceleration
    if (_space) {
      // for our ship, 0 degrees means pointing straight up, so we adjust to get a real angle
      float shipAngle = _orient + FloatMath.PI/2;
      // the thrust is coming out the back, so it's 180 degrees from the ship angle
      float thrustAngle = shipAngle - FloatMath.PI;
      // now we decompose the thrust into x and y components
      float tx = FloatMath.cos(thrustAngle), ty = FloatMath.sin(thrustAngle);
      // and apply it to our velocity
      _vel.x += tx * ACCEL;
      _vel.y += ty * ACCEL;
    }

    // apply our current velocity to the ship's position
    float x = _ship.tx(), y = _ship.ty();
    float nx = x + delta * _vel.x, ny = y + delta * _vel.y;
    // wrap around if we hit the edge of the display
    float width = graphics().width(), height = graphics().height();
    if (nx < 0) nx += width;
    else if (nx > width) nx -= width;
    if (ny < 0) ny += height;
    else if (ny > height) ny -= height;
    // finally update the ship sprite with the new position
    _ship.setTranslation(nx, ny);
  }

  @Override
  public void paint(float alpha) {
    _clock.paint(alpha);
  }

  protected CanvasImage createShip (float width, float height) {
    CanvasImage image = graphics().createImage(width, height);
    Path path = image.canvas().createPath();
    path.moveTo(width/2, 0).lineTo(width, height).lineTo(0, height).close();
    image.canvas().setFillColor(0xFFFFFFFF).fillPath(path);
    return image;
  }

  protected final ImmediateLayer.Renderer _starRenderer = new ImmediateLayer.Renderer() {
    protected final Random _rando = new Random();
    protected final Point[] _stars;
    protected float _nextTwinkle;

    /*ctor*/ {
      _stars = new Point[50];
      for (int ii = 0; ii < _stars.length; ii++) _stars[ii] = randomize(new Point());
    }

    public void render (Surface surf) {
      surf.setFillColor(0xFFFFFFFF);
      for (Point p : _stars) {
        surf.fillRect(p.x, p.y, 2, 2);
      }
      if (_clock.time() > _nextTwinkle) {
        randomize(_stars[_rando.nextInt(_stars.length)]);
        _nextTwinkle = _clock.time() + 500 + _rando.nextInt(1500); // twinkle a star every 1/2 to 2s
      }
    }

    private Point randomize (Point p) {
      p.x = _rando.nextFloat()*graphics().width();
      p.y = _rando.nextFloat()*graphics().height();
      return p;
    }
  };
}
