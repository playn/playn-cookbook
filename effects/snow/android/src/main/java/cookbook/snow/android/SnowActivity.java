package cookbook.snow.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import cookbook.snow.core.Snow;

public class SnowActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new Snow());
  }
}
