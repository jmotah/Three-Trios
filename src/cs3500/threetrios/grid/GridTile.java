package cs3500.threetrios.grid;

import cs3500.threetrios.cards.CardCompass;
import cs3500.threetrios.cards.PlayingCard;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.player.Player;

/**
 * A class to represent an individual grid tile on the game's grid.
 */
public class GridTile implements Grid {
  private final CellType cellType;
  private final PlayingCard cellCard;
  private final Player cellPlayer;

  /**
   * The GridTile class constructor.
   *
   * @param cellType the cellType of the tile
   */
  public GridTile(CellType cellType) {
    if (cellType == null) {
      throw new IllegalArgumentException("Your cell type cannot be null!");
    }

    if (cellType == CellType.HOLE ||
            cellType == CellType.CARD_CELL) {
      this.cellType = cellType;
      this.cellCard = null;
      this.cellPlayer = null;
    } else {
      throw new IllegalArgumentException("If you are defining a player cell, then you need" +
              "to define a cell card and cell player along with that! Use the other constructor" +
              "for this cell type!");
    }
  }

  /**
   * The GridTile class constructor.
   *
   * @param cellType   the cellType of the tile
   * @param cellCard   the PlayingCard object associated with the grid tile
   * @param cellPlayer the Players object associated with the grid tile
   */
  public GridTile(CellType cellType, PlayingCard cellCard, Player cellPlayer) {
    if (cellType == null) {
      throw new IllegalArgumentException("Your cell type cannot be null!");
    } else if (cellType == CellType.PLAYER_CELL &&
            (cellCard == null || cellPlayer == null)) {
      throw new IllegalArgumentException("Your cell card and cell player cannot be null when" +
              "you also provide a cell type of PLAYER_CELL!");
    }

    if (cellType == CellType.HOLE ||
            cellType == CellType.CARD_CELL) {
      this.cellType = cellType;
      this.cellCard = null;
      this.cellPlayer = null;
    } else {
      this.cellType = cellType;
      this.cellCard = cellCard;
      this.cellPlayer = cellPlayer;
    }
  }

  /**
   * Gets the cell type of this GridTile object.
   *
   * @return the tile's associated cell type
   */
  @Override
  public CellType getCellType() {
    return cellType;
  }

  /**
   * Gets the playing card of this GridTile object.
   *
   * @return the tile's associated playing card
   */
  @Override
  public PlayingCard getPlayingCard() {
    return cellCard;
  }

  /**
   * Gets the player of this GridTile object.
   *
   * @return the tile's associated player
   */
  @Override
  public Player getWhichPlayersTile() {
    return cellPlayer;
  }

  /**
   * Compares this GridTile object to the given other GridTile object.
   *
   * @param other            the GridTile object to compare to this one
   * @param compareDirection the compass direction to look at for this GridTile object
   * @return the GridTile object with greater value
   */
  @Override
  public GridTile compareTo(GridTile other, CardCompass compareDirection) {
    if (other == null) {
      throw new IllegalArgumentException("You can't compare a null grid tile!");
    } else if (this.cellCard == null && other.cellCard == null) {
      throw new IllegalArgumentException("Cannot compare tiles of both null cards");
    } else if (this.cellType != CellType.PLAYER_CELL || other.cellType != CellType.PLAYER_CELL) {
      throw new IllegalArgumentException("This GridTile object and the given other GridTile " +
              "object just be of cell type PLAYER_CELL!");
    } else if (this.cellCard == null) {
      return other;
    } else if (other.cellCard == null) {
      return this;
    } else if (compareDirection == null) {
      throw new IllegalArgumentException("Cannot compare tiles of a null direction!");
    }

    CardCompass oppositeDirection = compareDirection.oppositeDirection();

    if (this.cellCard.getValue(compareDirection) >
            other.cellCard.getValue(oppositeDirection)) {
      return this;
    } else {
      return other;
    }
  }

  /**
   * The translation of a GridTile to text.
   *
   * @return a String of a GridTile object translated to text
   */
  @Override
  public String toString() {
    return "|C:" + cellType + cellPlayer + cellCard;
  }
}
