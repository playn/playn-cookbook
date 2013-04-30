package cookbook.topeditor.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import cookbook.topeditor.core.TopEditor;

public class TopEditorActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new TopEditor());
  }
}
