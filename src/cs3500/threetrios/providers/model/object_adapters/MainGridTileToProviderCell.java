package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;

public class MainGridTileToProviderCell implements Cell {

  private Grid mainTile;

  public MainGridTileToProviderCell(Grid mainTile) {
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
    return mainTile.getWhichPlayersTile() == null && mainTile.getCellType() == CellType.HOLE;
  }

  @Override
  public Card getCard() {
    return new MainCardToProviderCard(mainTile.getPlayingCard());
  }

  @Override
  public void placeCard(Card card) {
    Cards playingCard = new ProviderCardToMainCard(card);

    mainTile = new GridTile(CellType.PLAYER_CELL,
            playingCard,
            mainTile.getWhichPlayersTile());
  }

  @Override
  public Cell copy() {
    return new MainGridTileToProviderCell(this.mainTile);
  }
}