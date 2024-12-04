package cs3500.threetrios.providers.model.object_adapters;

import java.util.List;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Player;

public class ProviderCellToMainGridTile implements Grid {

  private Cell providerCell;

  public ProviderCellToMainGridTile(Cell providerCell) {
    this.providerCell = providerCell;
  }

  @Override
  public CellType getCellType() {
    if (providerCell.isHole()) {
      return CellType.HOLE;
    } else if (providerCell.getCard() == null) {
      return CellType.CARD_CELL;
    } else {
      return CellType.PLAYER_CELL;
    }
  }

  @Override
  public Cards getPlayingCard() {
    return new ProviderCardToMainCard(providerCell.getCard());
  }

  @Override
  public Players getWhichPlayersTile() {
    return null; //CANNOT ADAPT
  }

  @Override
  public Grid compareTo(Grid other, CardCompass compareDirection) {
    return null; //CANNOT ADAPT
  }
}