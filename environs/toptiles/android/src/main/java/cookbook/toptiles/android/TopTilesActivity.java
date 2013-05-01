package cookbook.toptiles.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import cookbook.toptiles.core.TopTiles;

public class TopTilesActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new TopTiles());
  }
}
