package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;

/**
 * Adapts the project's main Grid interface and class implementation to the provider's Cell
 * interface. Adapts the primary implementation to a provider's interface.
 */
public class MainGridTileToProviderCell implements Cell {

  private Grid mainTile;

  /**
   * MainGridTileToProviderCell class constructor.
   *
   * @param mainTile the main Grid interface implementation class to delegate to
   */
  public MainGridTileToProviderCell(Grid mainTile) {
    if (mainTile == null) {
      throw new IllegalArgumentException("mainTile cannot be null");
    }

    this.mainTile = mainTile;
  }

  /**
   * Checks if the cell is a hole (an empty space where a card cannot be placed) by delegating.
   *
   * @return true if the cell is a hole; false otherwise
   */
  @Override
  public boolean isHole() {
    if (mainTile.getCellType() == CellType.HOLE) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if the cell is empty (contains no card) by delegating.
   *
   * @return true if the cell is empty; false otherwise
   */
  @Override
  public boolean isEmpty() {
    return mainTile.getPlayingCard() == null;
  }

  /**
   * Retrieves the card currently placed in the cell by delegating.
   *
   * @return the Card in the cell, or null if the cell is empty
   */
  @Override
  public Card getCard() {
    if (mainTile.getPlayingCard() == null) {
      return null;
    }

    return new MainCardToProviderCard(mainTile.getPlayingCard());
  }

  /**
   * Places a card in the cell by delegating.
   *
   * @param card the Card to be placed in the cell
   * @throws IllegalArgumentException if the cell is not empty or is a hole
   */
  @Override
  public void placeCard(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("card cannot be null");
    }

    Cards playingCard = new ProviderCardToMainCard(card);

    mainTile = new GridTile(CellType.PLAYER_CELL,
            playingCard,
            mainTile.getWhichPlayersTile());
  }

  /**
   * Creates a copy of this Cell by delegating.
   *
   * @return a new Cell instance with the same hole status as the original,
   * but no card placed in it.
   */
  @Override
  public Cell copy() {
    return new MainGridTileToProviderCell(this.mainTile);
  }
}