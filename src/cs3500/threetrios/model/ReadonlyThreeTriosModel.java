package cs3500.threetrios.model;

import cs3500.threetrios.grid.GridTile;
import cs3500.threetrios.player.Player;
import cs3500.threetrios.player.PlayerColor;

/**
 * A "read only" model interface holding all the methods that do not mutate any data.
 */
public interface ReadonlyThreeTriosModel {

  boolean isGameOver();

  Player findWinningPlayer(); //returns null when there is a tie

  GridTile[][] getGrid();

  Player getCurrentTurnPlayer();

  Player getPlayerOfColor(PlayerColor color);
}