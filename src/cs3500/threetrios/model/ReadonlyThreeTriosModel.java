package cs3500.threetrios.model;

import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.player.PlayerColor;

/**
 * A "read only" model interface holding all the methods that do not mutate any data.
 */
public interface ReadonlyThreeTriosModel {

  boolean isGameOver();

  Players findWinningPlayer(); //returns null when there is a tie

  GridTile[][] getGrid();

  Players getCurrentTurnPlayer();

  Players getPlayerOfColor(PlayerColor color);
}