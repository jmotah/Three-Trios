package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;

public class MainGridTileToProviderCell implements Cell {

  private GridTile mainTile;

  public MainGridTileToProviderCell(GridTile mainTile) {
    this.mainTile = mainTile;
  }

  @Override
  public boolean isHole() {
    if (mainTile.getCellType() == CellType.HOLE) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean isEmpty() {
    if (mainTile.getWhichPlayersTile() == null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Card getCard() {
    return null;
  }

  @Override
  public void placeCard(Card card) {
    return;
  }

  @Override
  public Cell copy() {
    return new MainGridTileToProviderCell(this.mainTile);
  }
}