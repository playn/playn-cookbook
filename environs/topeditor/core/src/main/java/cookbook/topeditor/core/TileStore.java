package cookbook.topeditor.core;

import static playn.core.PlayN.*;

public class TileStore {

  private static final String SKEY = "tiles";

  public void load (int[] tiles) {
    String data = storage().getItem(SKEY);
    if (data != null) {
      String[] values = data.split("\t");
      for (int ii = 0; ii < Math.min(values.length, tiles.length); ii++) {
        tiles[ii] = Integer.parseInt(values[ii]);
      }
    }
  }

  public void store (int[] tiles) {
    storage().setItem(SKEY, encode(tiles, "\t"));
  }

  public void dump (int[] tiles) {
    log().info(encode(tiles, ", "));
  }

  private String encode (int[] tiles, String sep) {
    StringBuilder buf = new StringBuilder();
    for (int tile : tiles) {
      if (buf.length() > 0) buf.append(sep);
      buf.append(tile);
    }
    return buf.toString();
  }
}
