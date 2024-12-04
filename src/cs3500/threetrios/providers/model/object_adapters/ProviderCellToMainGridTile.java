package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.providers.model.Cell;

/**
 * Adapts the provider's Cell interface and class implementation to the project's main Grid
 * interface. Adapts the provider's implementation to the primary interface.
 */
public class ProviderCellToMainGridTile implements Grid {

  private final Cell providerCell;

  /**
   * ProviderCellToMainGridTile class constructor,
   *
   * @param providerCell the provider's Cell interface implementation class to delegate to
   */
  public ProviderCellToMainGridTile(Cell providerCell) {
    if (providerCell == null) {
      throw new IllegalArgumentException("Provider cell cannot be null.");
    }

    this.providerCell = providerCell;
  }

  /**
   * Gets the cell type of this GridTile object by delegating.
   *
   * @return the tile's associated cell type
   */
  @Override
  public CellType getCellType() {
    if (providerCell.isHole()) {
      return CellType.HOLE;
    } else if (providerCell.getCard() == null) {
      return CellType.CARD_CELL;
    } else if (!providerCell.isHole() && providerCell.getCard() != null) {
      return CellType.PLAYER_CELL;
    }
    return CellType.HOLE;
  }

  /**
   * Gets the playing card of this GridTile object by delegating.
   *
   * @return the tile's associated playing card
   */
  @Override
  public Cards getPlayingCard() {
    if (providerCell.getCard() == null) {
      return null;
    }

    return new ProviderCardToMainCard(providerCell.getCard());
  }

  /**
   * Gets the player of this GridTile object by delegating. This cannot be adapted.
   *
   * @return the tile's associated player
   */
  @Override
  public Players getWhichPlayersTile() {
    return null; //CANNOT ADAPT
  }

  /**
   * Compares this GridTile object to the given other GridTile object by delegating. This cannot
   * be adapted.
   *
   * @param other            the GridTile object to compare to this one
   * @param compareDirection the compass direction to look at for this GridTile object
   * @return the GridTile object with greater value
   */
  @Override
  public Grid compareTo(Grid other, CardCompass compareDirection) {
    if (other == null || compareDirection == null) {
      throw new IllegalArgumentException("Grid and CardCompass objects cannot be null.");
    }
    return null; //CANNOT ADAPT
  }
}