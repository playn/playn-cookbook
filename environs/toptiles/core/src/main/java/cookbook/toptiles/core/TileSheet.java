package cookbook.toptiles.core;

import playn.core.Image;
import static playn.core.PlayN.assets;

public class TileSheet {

  private final Image[] _tiles;

  public final Image sheet;
  public final int tileX, tileY, tileWidth, tileHeight;

  public TileSheet (String path, int tileX, int tileY, int tileWidth, int tileHeight) {
    sheet = assets().getImage(path);
    this.tileX = tileX;
    this.tileY = tileY;
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
    _tiles = new Image[tileX*tileY];
  }

  public int tileCount () {
    return tileX * tileY;
  }

  public int index (int x, int y) {
    return y * tileX + x;
  }

  public Image tile (int idx) {
    return tile(idx, idx % tileX, idx / tileX);
  }

  public Image tile (int x, int y) {
    return tile(index(x, y), x, y);
  }

  private Image tile (int idx, int x, int y) {
    Image tile = _tiles[idx];
    if (tile == null) tile = (
      _tiles[idx] = sheet.subImage(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
    return tile;
  }
}
