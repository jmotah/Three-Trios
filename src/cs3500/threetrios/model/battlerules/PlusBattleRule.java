package cs3500.threetrios.model.battlerules;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;

/**
 * Represents the "Plus" battle rule. This rule is performed before the combo step only. It analyzes
 * the adjacent cells and figures out if the sum of the values have the same
 * directional sum in their respective opposite directions. This rule can only be satisfied if
 * there are at least two adjacent cards with the same sum.
 */
public class PlusBattleRule implements BattleRules {

  /**
   * Applies the "Plus" battle rule to model grid to a specifically placed card at the provided
   * row and column indices. Finds all the card positions that satisfy the "Plus" rule.
   *
   * @param row    the row index of the placed card
   * @param column the column index of the placed card
   * @param grid   the current game grid to consult
   * @return a list of Point objects representing the row and column positions of each card to be
   * flipped
   */
  @Override
  public List<Point> applyRule(int row, int column, Grid[][] grid) {
    return determineGreatestNumberOfPointsWithSameSum(row, column, grid);
  }

  /**
   * Analyzes all adjacent cards and their directional sums. Determines the cards with the same
   * sums. For cards with that sum appear 2 or more times, they are returned in through a list of
   * Point objects representing the positions of grid tiles to have their ownership changed, or be
   * flipped.
   *
   * @param row    the row index of the placed card
   * @param column the column index of the placed card
   * @param grid   the current game grid to consult
   * @return a list of Point objects representing the row and column positions of each card to be
   * flipped
   */
  private List<Point> determineGreatestNumberOfPointsWithSameSum(int row, int column, Grid[][] grid) {
    HashMap<Point, Integer> cellsAndDirectionalSums = getAdjacentCellsWithDirectionalSum(row, column, grid);

    List<Point> greatestNumOfPointsWithSameSum = new ArrayList<>();

    for (HashMap.Entry<Point, Integer> entry : cellsAndDirectionalSums.entrySet()) {
      int sum = entry.getValue();
      List<Point> pointsWithSameSum = findPointsWithSameSum(cellsAndDirectionalSums, sum);

      if (pointsWithSameSum.size() > greatestNumOfPointsWithSameSum.size()) {
        greatestNumOfPointsWithSameSum = pointsWithSameSum;
      } else if (pointsWithSameSum.size() == 2 && greatestNumOfPointsWithSameSum.size() == 2) {
        greatestNumOfPointsWithSameSum.addAll(pointsWithSameSum);
      }
    }
    return greatestNumOfPointsWithSameSum;
  }

  /**
   * Finds all the points with the same directional sum as the given sum.
   *
   * @param cellsAndDirectionalSums a map of grid positions as Point objects with their respective
   *                                directional sums
   * @param sum                     the sum to compare the entry values against
   * @return a list of Point objects representing the positions of the grid that match the given sum
   */
  private List<Point> findPointsWithSameSum(HashMap<Point, Integer> cellsAndDirectionalSums, int sum) {
    List<Point> pointsWithSameSum = new ArrayList<>();

    for (HashMap.Entry<Point, Integer> compareEntry : cellsAndDirectionalSums.entrySet()) {
      if (compareEntry.getValue() == sum) {
        pointsWithSameSum.add(compareEntry.getKey());
      }
    }
    return pointsWithSameSum;
  }

  /**
   * Calculates the directional sum of all adjacent cells to the given row and column index and
   * places those Points and directional sums in a HashMap object.
   *
   * @param row    the row index of the placed card
   * @param column the column index of the placed card
   * @param grid   the current game grid to consult
   * @return a HashMap object of all adjacent cells to the placed card with their positions on the
   * grid stored in a Point object and their respective directional sums
   */
  private HashMap<Point, Integer> getAdjacentCellsWithDirectionalSum(
          int row, int column, Grid[][] grid) {
    Grid attackerTile = grid[row][column];

    HashMap<Point, Integer> cellsAndDirectionalSums = new HashMap<>();

    getCellPointAndDirectionalSum(attackerTile,
            row - 1, column, CardCompass.NORTH_VALUE, grid,
            cellsAndDirectionalSums);

    getCellPointAndDirectionalSum(attackerTile,
            row + 1, column, CardCompass.SOUTH_VALUE, grid,
            cellsAndDirectionalSums);

    getCellPointAndDirectionalSum(attackerTile,
            row, column + 1, CardCompass.EAST_VALUE, grid,
            cellsAndDirectionalSums);

    getCellPointAndDirectionalSum(attackerTile,
            row, column - 1, CardCompass.WEST_VALUE, grid,
            cellsAndDirectionalSums);

    return cellsAndDirectionalSums;
  }

  /**
   * Puts the adjacent cell's point and calculated sum into the provided hash map object if the
   * given row and column index are valid.
   *
   * @param attackerTile           the grid tile of the placed card
   * @param row                    the row index of the adjacent card
   * @param column                 the column index of the adjacent card
   * @param compareDirection       the respective direction's value to analyze
   * @param grid                   the current game grid to consult
   * @param cellsAndDirectionalSum the HashMap object of Point objects and their directional sums
   */
  private void getCellPointAndDirectionalSum(
          Grid attackerTile,
          int row, int column,
          CardCompass compareDirection,
          Grid[][] grid,
          HashMap<Point, Integer> cellsAndDirectionalSum) {
    if (row >= 0 && row < grid.length &&
            column >= 0 && column < grid[0].length &&
            grid[row][column].getCellType() == CellType.PLAYER_CELL) {
      Grid defenderTile = grid[row][column];

      cellsAndDirectionalSum.put(
              new Point(row, column),
              returnSumForCompareDirection(attackerTile, defenderTile, compareDirection));

    }
  }

  /**
   * Calculates the sum between two placed grid cells respective to the given compare direction.
   * The respective opposite direction is analyzed by the defender tile.
   *
   * @param attackerTile     a grid tile of a placed card
   * @param defenderTile     a grid tile of a placed card
   * @param compareDirection the respective direction's value to analyze
   * @return an integer of the summation of attack values for the respective directions
   */
  private int returnSumForCompareDirection(Grid attackerTile, Grid defenderTile,
                                           CardCompass compareDirection) {
    CardCompass oppositeDirection = compareDirection.oppositeDirection();

    return attackerTile.getPlayingCard().getValue(compareDirection) +
            defenderTile.getPlayingCard().getValue(oppositeDirection);
  }
}