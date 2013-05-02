package cookbook.dungeon.core;

import playn.core.*;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

public class DungeonGame extends Game.Default {

  private final Clock.Source _clock = new Clock.Source(33);
  private final DungeonView _view = new DungeonView(new Dungeon(24, 12));
  private final Avatar _avatar = new Avatar(_view);

  public DungeonGame() {
    super(33); // call update every 33ms (30 times per second)
  }

  @Override
  public void init() {
    // listen for keypresses
    keyboard().setListener(new Keyboard.Adapter() {
      @Override public void onKeyDown (Keyboard.Event event) {
        if (_avatar.onKey(event.key(), true)) {
          // use prevent default to stop the web page from scrolling when arrow keys are pressed
          event.flags().setPreventDefault(true);
        }
      }
      @Override public void onKeyUp (Keyboard.Event event) {
        if (_avatar.onKey(event.key(), false)) {
          event.flags().setPreventDefault(true);
        }
      }
    });

    _view.init();
    _avatar.init();
  }

  @Override
  public void update(int delta) {
    _clock.update(delta);
  }

  @Override
  public void paint(float alpha) {
    _clock.paint(alpha);
    _avatar.paint(_clock);
  }
}
