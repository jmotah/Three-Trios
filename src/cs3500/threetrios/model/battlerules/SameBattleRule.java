package cs3500.threetrios.model.battlerules;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.PlayerColor;

public class SameBattleRule implements BattleRules {

  //get all of the adjacent cells
  // compare the adjacent cells opposite values
  // if the values are equal, add them to a list
  // check if more than one values are equal, if true then swap the grid tile color
  // manage battling
  // perform regular battle strategy

  public List<Point> applyRule(int row, int column, Grid[][] grid) {
    return getMatchingNumberAdjacentTiles(row, column, grid);
  }

  private List<Point> getMatchingNumberAdjacentTiles(int row, int column, Grid[][] grid) {
    List<Point> matchingNumbers = new ArrayList<>();

    Grid attackerTile = grid[row][column];

    if (getAdjacentGridTileSame(attackerTile, row - 1, column,
            CardCompass.NORTH_VALUE, grid)) {
      matchingNumbers.add(new Point(row - 1, column));
    }

    if (getAdjacentGridTileSame(attackerTile, row + 1, column,
            CardCompass.SOUTH_VALUE, grid)) {
      Grid matchedTile = grid[row + 1][column];
      matchingNumbers.add(new Point(row + 1, column));
    }

    if (getAdjacentGridTileSame(attackerTile, row, column + 1,
            CardCompass.EAST_VALUE, grid)) {
      Grid matchedTile = grid[row][column + 1];
      matchingNumbers.add(new Point(row, column + 1));
    }

    if (getAdjacentGridTileSame(attackerTile, row, column - 1,
            CardCompass.WEST_VALUE, grid)) {
      Grid matchedTile = grid[row][column - 1];
      matchingNumbers.add(new Point(row, column - 1));
    }
    return matchingNumbers;
  }

  private boolean getAdjacentGridTileSame(Grid attackerTile,
                                          int row, int column,
                                          CardCompass compareDirection,
                                          Grid[][] grid) {
    if (row >= 0 && row < grid.length &&
            column >= 0 && column < grid[0].length &&
            grid[row][column].getCellType() == CellType.PLAYER_CELL) {
      Grid defenderTile = grid[row][column];

      return sameNumberForOppositeDirections(attackerTile, defenderTile, compareDirection);

    }
    return false;
  }

  private boolean sameNumberForOppositeDirections(Grid attackerTile, Grid defenderTile,
                                                  CardCompass compareDirection) {
    CardCompass oppositeDirection = compareDirection.oppositeDirection();

    if (attackerTile.getPlayingCard().getValue(compareDirection) ==
            defenderTile.getPlayingCard().getValue(oppositeDirection)) {
      return true;
    }
    return false;
  }
}