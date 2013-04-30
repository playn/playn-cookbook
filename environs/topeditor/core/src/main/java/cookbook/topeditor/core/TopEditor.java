package cookbook.topeditor.core;

import playn.core.*;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class TopEditor extends Game.Default {

  private final Clock.Source _clock = new Clock.Source(33);
  private final TileSheet _sheet = new TileSheet("images/tiles.png", 16, 16, 32, 32);
  private final TileStore _store = new TileStore();
  private final Camera _camera = new Camera();
  private final TileView _view = new TileView(_sheet, _camera, _store, 50, 37);
  private final TileChooser _chooser = new TileChooser(_sheet);
  private final TilePainter _painter = new TilePainter(_view, _chooser);

  public TopEditor() {
    super(33); // call update every 33ms (30 times per second)
  }

  @Override
  public void init() {
    // dispatch keypresses to things that need them
    keyboard().setListener(new Keyboard.Adapter() {
      @Override public void onKeyDown (Keyboard.Event event) {
        if (_camera.onKey(event.key(), true) ||
            _painter.onKey(event.key(), true)) {
          // we use prevent default here to stop the web page from scrolling when the user presses
          // the arrow keys; we're using the arrow keys
          event.flags().setPreventDefault(true);
        }
      }
      @Override public void onKeyUp (Keyboard.Event event) {
        if (_camera.onKey(event.key(), false) ||
            _painter.onKey(event.key(), false)) {
          event.flags().setPreventDefault(true);
        }
      }
    });

    // init our tile view
    _view.init();
  }

  @Override
  public void update(int delta) {
    _clock.update(delta);
    _view.update(_clock);
  }

  @Override
  public void paint(float alpha) {
    _clock.paint(alpha);
    _camera.paint(_clock);
  }
}
