package cookbook.topeditor.core;

import pythagoras.i.MathUtil;

import playn.core.*;
import static playn.core.PlayN.*;

public class TileChooser implements Pointer.Listener {

  private final TileSheet _sheet;
  private final ImageLayer _pad, _selector, _cursor;
  private int _selX, _selY; // the current cursor position

  public int selTileIdx; // the index of the currently selected tile

  public TileChooser (TileSheet sheet) {
    _sheet = sheet;

    // draw a background behind our "current tile" pad (or the whole selector if that's viz)
    graphics().rootLayer().add(graphics().createImmediateLayer(new ImmediateLayer.Renderer() {
      public void render (Surface surf) {
        surf.setFillColor(0xFFFFFFFF);
        if (_selector.visible()) surf.fillRect(0, 0, _selector.width()+2, _selector.height()+2);
        else surf.fillRect(0, 0, _sheet.tileWidth+2, _sheet.tileHeight+2);
      }
    }));

    // create a layer for displaying our currently selected tile
    _pad = graphics().createImageLayer(_sheet.tile(selTileIdx));
    _pad.setDepth(1); // render above the main tile view
    _pad.addListener(this);
    graphics().rootLayer().addAt(_pad, 0, 0);

    // create a layer to display our full tile sheet when we're selecting a new tile
    _selector = graphics().createImageLayer(_sheet.sheet);
    _selector.setVisible(false);
    _selector.setDepth(2); // render above the pad
    graphics().rootLayer().add(_selector);

    // create a cursor layer for use when we are selecting a new tile
    CanvasImage cimg = graphics().createImage(_sheet.tileWidth, _sheet.tileHeight);
    cimg.canvas().setStrokeWidth(4).setStrokeColor(0xFFFF0000).
      strokeRect(0, 0, cimg.width(), cimg.height());
    _cursor = graphics().createImageLayer(cimg);
    _cursor.setVisible(false);
    _cursor.setDepth(3); // render above the selector
    graphics().rootLayer().add(_cursor);
  }

  @Override public void onPointerStart (Pointer.Event event) {
    _selector.setVisible(true);
    _cursor.setVisible(true);
    onPointerDrag(event);
  }

  @Override public void onPointerDrag (Pointer.Event event) {
    int selX = (int)event.x() / _sheet.tileWidth, selY = (int)event.y() / _sheet.tileHeight;
    // compute the coordinate of the tile under the pointer
    _selX = MathUtil.clamp(selX, 0, _sheet.tileX-1);
    _selY = MathUtil.clamp(selY, 0, _sheet.tileY-1);
    // move the cursor sprite to those coordinates
    _cursor.setTranslation(_selX * _sheet.tileWidth, _selY * _sheet.tileHeight);
    // note our currently selected tile
    selTileIdx = _sheet.index(_selX, _selY);
  }

  @Override public void onPointerEnd (Pointer.Event event) {
    _selector.setVisible(false);
    _cursor.setVisible(false);
    // update our "current selection" pad with the currently selected tile
    _pad.setImage(_sheet.tile(selTileIdx));
  }

  @Override public void onPointerCancel (Pointer.Event event) {
    onPointerEnd(event);
  }
}
