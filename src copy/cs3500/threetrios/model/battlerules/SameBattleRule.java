package cs3500.threetrios.model.battlerules;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;

/**
 * Represents the "Same" battle rule. This rule is performed before the combo step only. It
 * analyzes the adjacent cells and analyzes if there are any tiles with the same values as the
 * placed card and the adjacent cards in respective to their opposing directions. These matched
 * values do not have to be the same for analysis of differing adjacent cards. The rule allows for
 * card flipping of the found tiles only if there are at least two adjacent cards with same
 * directional values.
 */
public class SameBattleRule implements BattleRules {

  /**
   * Applies the "Same" battle rule to the model grid to a specifically placed card at the provided
   * row and column indices. Finds all the card positions that satisfy the "Same" rule and returns
   * them in a list of Point objects.
   *
   * @param row    the row index of the placed card
   * @param column the column index of the placed card
   * @param grid   the current game grid to consult
   * @return a list of Point objects representing the row and column positions of each card to be
   *     flipped
   */
  public List<Point> applyRule(int row, int column, Grid[][] grid) {
    return getMatchingNumberAdjacentTiles(row, column, grid);
  }

  /**
   * Finds all adjacent cards whose attack values respective of compare direction satisfy the
   * rule criteria. The positions of all cells that satisfy the criteria are stored in Point
   * objects.
   *
   * @param row    the row index of the placed card
   * @param column the column index of the placed card
   * @param grid   the current game grid to consult
   * @return a list of positions of all cells that satisfy the rule criteria stored in
   *     Point objects
   */
  private List<Point> getMatchingNumberAdjacentTiles(int row, int column, Grid[][] grid) {
    List<Point> matchingNumbers = new ArrayList<>();

    Grid attackerTile = grid[row][column];

    if (getAdjacentGridTileSame(attackerTile, row - 1, column,
            CardCompass.NORTH_VALUE, grid)) {
      matchingNumbers.add(new Point(row - 1, column));
    }

    if (getAdjacentGridTileSame(attackerTile, row + 1, column,
            CardCompass.SOUTH_VALUE, grid)) {
      matchingNumbers.add(new Point(row + 1, column));
    }

    if (getAdjacentGridTileSame(attackerTile, row, column + 1,
            CardCompass.EAST_VALUE, grid)) {
      matchingNumbers.add(new Point(row, column + 1));
    }

    if (getAdjacentGridTileSame(attackerTile, row, column - 1,
            CardCompass.WEST_VALUE, grid)) {
      matchingNumbers.add(new Point(row, column - 1));
    }
    return matchingNumbers;
  }

  /**
   * Checks to ensure that an adjacent cell is a valid cell to look at in the grid. Checks to see
   * if the attack values respective of the given compare direction are the same or not.
   *
   * @param attackerTile     a grid tile of the placed card
   * @param row              the row index of the adjacent card
   * @param column           the column index of the adjacent card
   * @param compareDirection the respective direction's value to analyze
   * @param grid             the current game grid to consult
   * @return true if the attack values respective of the compare direction are the same; otherwise
   *     return false
   */
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

  /**
   * Checks to see if the attack values of two given grid cells respective to their given compare
   * direction are equal or not. The respective opposing direction is analyzed by the defender tile.
   *
   * @param attackerTile     a grid tile of a placed card
   * @param defenderTile     a grid tile of a placed card
   * @param compareDirection the respective direction value to analyze
   * @return true if the attack values respective of the given compare direction are equal, false
   *     if otherwise
   */
  private boolean sameNumberForOppositeDirections(Grid attackerTile, Grid defenderTile,
                                                  CardCompass compareDirection) {
    CardCompass oppositeDirection = compareDirection.oppositeDirection();

    return attackerTile.getPlayingCard().getValue(compareDirection) ==
            defenderTile.getPlayingCard().getValue(oppositeDirection);
  }
}