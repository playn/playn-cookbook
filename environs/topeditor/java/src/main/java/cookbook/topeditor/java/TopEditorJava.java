package cookbook.topeditor.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import cookbook.topeditor.core.TopEditor;

public class TopEditorJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    config.width  = 800;
    config.height = 600;
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new TopEditor());
  }
}
