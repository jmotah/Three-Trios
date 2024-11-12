package cs3500.threetrios.model.grid;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.player.Players;

/**
 * Represents a grid for the ThreeTrios game. A valid grid has the option of possessing a hole
 * and/or card cells on initialization. Playing cards can be played to the grid tile type of
 * card cells, but cannot be played to any holes. In addition, a valid grid possesses a strictly
 * odd number of card cells upon initialization which uses an invariance to ensure this.
 */
public interface Grid {
  CellType getCellType();

  PlayingCard getPlayingCard();

  Players getWhichPlayersTile();

  GridTile compareTo(GridTile other, CardCompass compareDirection);
}
