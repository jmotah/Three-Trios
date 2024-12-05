package cs3500.threetrios.model;

import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.player.PlayerColor;

/**
 * A "read only" model interface holding all the methods that do not mutate any data.
 */
public interface ReadonlyThreeTriosModel {

  /**
   * Checks if the game is over or not. The game is over if every tile in the grid has a valid
   * playing card placed in it.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Finds the current winning player in the game. The winning player is chosen by which player
   * has the greatest number of cards in that player's name. This includes cards on the grid, and
   * cards in the player's hand. If there is a tie, null is returned.
   *
   * @return the winning player as a Players object
   */
  Players findWinningPlayer(); //returns null when there is a tie

  /**
   * Returns the winning player's score.
   *
   * @return an integer of the winning player's score
   */
  int findWinningPlayerScore();

  /**
   * Gets the current game grid.
   *
   * @return a copy of the game grid
   */
  Grid[][] getGrid();

  /**
   * Gets the current player's turn.
   *
   * @return the current player's turn
   */
  Players getCurrentTurnPlayer();

  /**
   * Given a player color, yields the respective Player object.
   *
   * @param color the player color of the player to return
   * @return the found Player object
   */
  Players getPlayerOfColor(PlayerColor color);
}