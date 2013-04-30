package cookbook.asteroids.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import cookbook.asteroids.core.Asteroids;

public class AsteroidsActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new Asteroids());
  }
}
