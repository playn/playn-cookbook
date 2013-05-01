package cookbook.toptiles.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import cookbook.toptiles.core.TopTiles;

public class TopTilesJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new TopTiles());
  }
}
