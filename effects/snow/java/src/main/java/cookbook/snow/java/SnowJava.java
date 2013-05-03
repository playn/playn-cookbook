package cookbook.snow.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import cookbook.snow.core.Snow;

public class SnowJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new Snow());
  }
}
