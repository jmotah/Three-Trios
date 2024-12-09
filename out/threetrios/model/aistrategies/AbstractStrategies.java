package cs3500.threetrios.model.aistrategies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * An abstract class with common methods for classes implementing strategies.
 */
public abstract class AbstractStrategies {

  /**
   * Compares two Point objects. Returns the uppermost, left-most Point object. If both
   * Points yield the same x and y values, then it returns the highestScorePoint Point object.
   * Points that are more upper (row closer to 0) take more priority than points that are more
   * left (columns closer to 0).
   *
   * @param highestScorePoint the first Point object
   * @param comparePosition   the second Point object
   * @return returns the uppermost, left-most Point object. If both Point objects yield the same x
   *         and y values, then it returns the highestScorePoint Point object.
   */
  protected Point comparePositions(Point highestScorePoint, Point comparePosition) {
    if (highestScorePoint == null) {
      return comparePosition;
    } else if (comparePosition == null) {
      return highestScorePoint;
    }

    if (highestScorePoint.getX() == comparePosition.getX()) { //compare columns
      return comparePositionsHelper(highestScorePoint, comparePosition);
    } else if (highestScorePoint.getX() < comparePosition.getX()) {
      return highestScorePoint;
    } else if (highestScorePoint.getX() > comparePosition.getX()) {
      return comparePosition;
    }
    return highestScorePoint;
  }

  /**
   * Compares the columns between two Point objects. Serves as a helper method for the
   * comparePositions method. Returns the left-most Point object. If both Point objects have the
   * same Y value, then it returns the highestScorePoint Point object.
   *
   * @param highestScorePoint the first Point object
   * @param comparePosition   the second Point object
   * @return returns the left-most Point object. If both Point objects have the
   *         same Y value, then it returns the highestScorePoint Point object
   */
  private Point comparePositionsHelper(Point highestScorePoint, Point comparePosition) {
    if (highestScorePoint == null) {
      return comparePosition;
    } else if (comparePosition == null) {
      return highestScorePoint;
    }

    if (highestScorePoint.getY() == comparePosition.getY()) {
      return highestScorePoint; //this will be the lesser index, so return it
    } else if (highestScorePoint.getY() < comparePosition.getY()) {
      return highestScorePoint;
    } else if (highestScorePoint.getY() > comparePosition.getY()) {
      return comparePosition;
    }
    return highestScorePoint;
  }

  /**
   * Gets all the possible moves on the grid.
   *
   * @return returns a list of Point objects. Each Point object represents the position of a
   *         tile on a grid.
   */
  protected List<Point> getAllPossibleMoves(ReadonlyThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    List<Point> allPossibleGridMoves = new ArrayList<>();
    for (int row = 0; row < model.getGrid().length; row++) {
      for (int column = 0; column < model.getGrid()[0].length; column++) {
        if (model.getGrid()[row][column].getCellType() == CellType.CARD_CELL) {
          allPossibleGridMoves.add(new Point(row, column));
        }
      }
    }
    return allPossibleGridMoves;
  }
}