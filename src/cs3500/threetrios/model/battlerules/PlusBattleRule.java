package cs3500.threetrios.model.battlerules;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;

public class PlusBattleRule implements BattleRules {

  @Override
  public List<Point> applyRule(int row, int column, Grid[][] grid) {
    return determineGreatestNumberOfPointsWithSameSum(row, column, grid);
  }

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

  private List<Point> findPointsWithSameSum(HashMap<Point, Integer> cellsAndDirectionalSums, int sum) {
    List<Point> pointsWithSameSum = new ArrayList<>();

    for (HashMap.Entry<Point, Integer> compareEntry : cellsAndDirectionalSums.entrySet()) {
      if (compareEntry.getValue() == sum) {
        pointsWithSameSum.add(compareEntry.getKey());
      }
    }
    return pointsWithSameSum;
  }

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

  private int returnSumForCompareDirection(Grid attackerTile, Grid defenderTile,
                                           CardCompass compareDirection) {
    CardCompass oppositeDirection = compareDirection.oppositeDirection();

    return attackerTile.getPlayingCard().getValue(compareDirection) +
            defenderTile.getPlayingCard().getValue(oppositeDirection);
  }
}