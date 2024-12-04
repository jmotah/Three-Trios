package cs3500.threetrios.model.grid;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.player.Players;

/**
 * Represents a grid for the ThreeTrios game. A valid grid has the option of possessing a hole
 * and/or card cells on initialization. Playing cards can be played to the grid tile type of
 * card cells, but cannot be played to any holes. In addition, a valid grid possesses a strictly
 * odd number of card cells upon initialization which uses an invariance to ensure this.
 */
public interface Grid {
  /**
   * Gets the cell type of this GridTile object.
   *
   * @return the tile's associated cell type
   */
  CellType getCellType();

  /**
   * Gets the playing card of this GridTile object.
   *
   * @return the tile's associated playing card
   */
  Cards getPlayingCard();

  /**
   * Gets the player of this GridTile object.
   *
   * @return the tile's associated player
   */
  Players getWhichPlayersTile();

  /**
   * Compares this GridTile object to the given other GridTile object.
   *
   * @param other            the GridTile object to compare to this one
   * @param compareDirection the compass direction to look at for this GridTile object
   * @return the GridTile object with greater value
   */
  Grid compareTo(Grid other, CardCompass compareDirection);
}
