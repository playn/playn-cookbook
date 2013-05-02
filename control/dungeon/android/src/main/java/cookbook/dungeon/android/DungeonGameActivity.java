package cookbook.dungeon.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import cookbook.dungeon.core.DungeonGame;

public class DungeonGameActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new DungeonGame());
  }
}
