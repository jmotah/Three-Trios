package cs3500.threetrios.model.aistrategies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;

/**
 * Implementation of strategy 2. Strategy 2 is the idea of playing the highest valued card possible
 * to the corners to eliminate the possibility of 2 of the corner sides to be battled against.
 * Priority is placed on placing the highest card possible when summed for the two "battle-able"
 * directions in the found corner position. If multiple corner positions are found, the uppermost
 * leftmost position is selected and the card is placed there. If all the corner positions are
 * taken, then the 0th index card is placed in the uppermost leftmost grid tile.
 */
public class Strategy2 extends AbstractStrategies implements Strategies {
  private final ReadonlyThreeTriosModel model;

  /**
   * Constructor for Strategy2 class.
   * @param model the model to gain immutable data from
   */
  public Strategy2(ReadonlyThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.model = model;
  }

  /**
   * Runs strategy 2 and looks for the best position as well as card to play at that position in
   * respect to the strategy. The Point object in the HashMap represents the position on the grid
   * while the Integer object in the Hashmap represents the card index to play from the current
   * player's hand.
   *
   * @return a HashMap object of a Point object and an Integer object where the Point object
   *         represents a tile on the grid to play the card at and the Integer object represents the
   *         card index to play from the current player's hand on that grid tile
   */
  @Override
  public HashMap<Point, Integer> runStrategy() {
    HashMap<Point, Integer> optimalMove = new HashMap<>();
    optimalMove.put(tieBreakCorner(), findBestCardIdxToPlay());
    return optimalMove;
  }

  /**
   * Finds the best card index to play. If the corner tie-break method returns a Point in a corner,
   * the method will return the card of highest value for the "battle-able" directions. If the Point
   * object returned is not a corner position, then it returns the best card index to play as the
   * 0th index card in the player's hand.
   *
   * @return the index of the card to play
   */
  public int findBestCardIdxToPlay() {
    List<Cards> hand = model.getCurrentTurnPlayer().getHand();
    Point bestCorner = tieBreakCorner();
    int bestCardIdx = 0;
    int highestDirectionalScore = 0;
    //INVARIANCE: only one item in hash map

    bestCardIdx = checkIfPositionIsACorner(bestCorner, hand, highestDirectionalScore, bestCardIdx);
    //base case is index = 0
    return bestCardIdx;
  }

  /**
   * Checks if the found tie-break Point object is in the corner or not. If it is, calculates the
   * greatest card to play to the corner. If not, returns 0.
   *
   * @param bestCorner              the position to check if it is a corner or not
   * @param hand                    the player's hand
   * @param highestDirectionalScore the highest directional score found from comparing cards
   * @param bestCardIdx             the best found card index
   * @return the card index that is greatest in value if played to the given position if it is a
   *         corner; if it is not a corner, returns 0
   */
  private int checkIfPositionIsACorner(Point bestCorner, List<Cards> hand,
                                       int highestDirectionalScore, int bestCardIdx) {
    if (bestCorner == null) {
      throw new IllegalArgumentException("Point object cannot be null!");
    } else if (hand == null) {
      throw new IllegalArgumentException("Hand cannot be null!");
    } else if (highestDirectionalScore < 0) {
      throw new IllegalArgumentException("Highest directional score cannot be negative!");
    } else if (bestCardIdx < 0 || bestCardIdx >= hand.size()) {
      throw new IllegalArgumentException("Card index is out of bounds!");
    }

    if (bestCorner.getX() == 0 && bestCorner.getY() == 0) {
      //East, South
      bestCardIdx = cornerOperations(hand,
              CardCompass.EAST_VALUE, CardCompass.SOUTH_VALUE,
              highestDirectionalScore, bestCardIdx);

    } else if (bestCorner.getX() == 0 && bestCorner.getY() == model.getGrid()[0].length - 1) {
      //West, South case

      bestCardIdx = cornerOperations(hand,
              CardCompass.WEST_VALUE, CardCompass.SOUTH_VALUE,
              highestDirectionalScore, bestCardIdx);
    } else if (bestCorner.getX() == model.getGrid().length - 1 && bestCorner.getY() == 0) {
      //North, east case

      bestCardIdx = cornerOperations(hand,
              CardCompass.NORTH_VALUE, CardCompass.EAST_VALUE,
              highestDirectionalScore, bestCardIdx);
    } else if (bestCorner.getX() == model.getGrid().length - 1 && bestCorner.getY() ==
            model.getGrid()[0].length - 1) {
      //North, west case

      bestCardIdx = cornerOperations(hand,
              CardCompass.NORTH_VALUE, CardCompass.WEST_VALUE,
              highestDirectionalScore, bestCardIdx);
    }
    return bestCardIdx;
  }

  /**
   * Calculates the greatest card to play to the corner. Checks if the found tie-break Point object
   * is in the corner or not. If it is, calculates the greatest card to play to the corner.
   * If not, returns 0.
   *
   * @param hand                    the current player's hand
   * @param direction1              one of the "battle-able" sides facing another grid tile
   * @param direction2              one of the "battle-able" sides facing another grid tile
   * @param highestDirectionalScore the highest score calculated from adding each directional card
   *                                value from the "battle-able" sides
   * @param bestCardIdx             the best found card's index
   * @return the card index with the greatest score calculated from adding each directional card
   *         value from the "battle-able" sides
   */
  private static int cornerOperations(List<Cards> hand, CardCompass direction1,
                                      CardCompass direction2, int highestDirectionalScore,
                                      int bestCardIdx) {

    if (hand == null) {
      throw new IllegalArgumentException("Hand cannot be null!");
    } else if (direction1 == null) {
      throw new IllegalArgumentException("Direction1 cannot be null!");
    } else if (direction2 == null) {
      throw new IllegalArgumentException("Direction2 cannot be null!");
    } else if (highestDirectionalScore < 0) {
      throw new IllegalArgumentException("Highest directional score cannot be negative!");
    } else if (bestCardIdx < 0 || bestCardIdx >= hand.size()) {
      throw new IllegalArgumentException("Card index is out of bounds!");
    }

    for (int i = 0; i < hand.size(); i++) {
      Cards card = hand.get(i);
      int cardDirectionalScore = card.getValue(direction1) +
              card.getValue(direction2);

      if (cardDirectionalScore > highestDirectionalScore) { //accounts for lower card index too
        highestDirectionalScore = cardDirectionalScore;
        bestCardIdx = i;
      }
    }
    return bestCardIdx;
  }

  /**
   * Performs a tie-break on the corners. If corners positions to play to are found, returns the
   * corner in the uppermost leftmost position on the grid. If no corner positions are found,
   * returns the uppermost leftmost position out of all possible playable spots on the grid.
   *
   * @return the uppermost leftmost position to play to out of the found corners; if no corners
   *         are found, it returns the uppermost leftmost position to play to out of every possible
   *         move on the grid
   */
  public Point tieBreakCorner() {
    List<Point> allCorners = findAllCorners();
    Point upperMostLeftMostOpenCorner = null;

    if (!allCorners.isEmpty()) {
      for (int corner = 0; corner < allCorners.size(); corner++) {
        if (upperMostLeftMostOpenCorner == null) {
          upperMostLeftMostOpenCorner = allCorners.get(corner);
        } else {
          upperMostLeftMostOpenCorner = comparePositions(
                  upperMostLeftMostOpenCorner,
                  allCorners.get(corner));
        }
      }
    } else {
      List<Point> allPossibilities = getAllPossibleMoves(model);
      for (int possibility = 0; possibility < allPossibilities.size(); possibility++) {
        if (upperMostLeftMostOpenCorner == null) {
          upperMostLeftMostOpenCorner = allPossibilities.get(possibility);
        } else {
          upperMostLeftMostOpenCorner = comparePositions(
                  upperMostLeftMostOpenCorner,
                  allPossibilities.get(possibility));
        }
      }
    }
    return upperMostLeftMostOpenCorner;
  }

  /**
   * Finds every possible corner position to play to on the grid.
   *
   * @return a list of Point objects with every possible corner position to play to on the grid
   */
  public List<Point> findAllCorners() {
    List<Point> allCorners = new ArrayList<>();

    List<Point> allPossibilities = getAllPossibleMoves(model);

    for (int possibility = 0; possibility < allPossibilities.size(); possibility++) {
      if (isACornerPosition(allPossibilities.get(possibility))) {
        allCorners.add(allPossibilities.get(possibility));
      }
    }
    return allCorners;
  }

  /**
   * Checks to see if the position is the corner position on the game grid.
   *
   * @param position the position on the grid; a Point object represents the position on the game
   *                 grid
   * @return true if the Point object is a corner position, false otherwise
   */
  public boolean isACornerPosition(Point position) {
    Grid[][] grid = model.getGrid();

    if (position == null) {
      return false;
    } else if (position.getX() < 0 ||
            position.getX() >= grid.length ||
            position.getY() < 0 ||
            position.getY() >= grid[0].length) {
      throw new IllegalArgumentException("Given position is out of bounds for the grid!");
    }

    return (position.getX() == 0 &&
            position.getY() == 0) ||

            (position.getX() == 0 &&
                    position.getY() == grid[0].length - 1) ||

            (position.getX() == grid.length - 1 &&
                    position.getY() == 0) ||

            (position.getX() == grid.length - 1 &&
                    position.getY() == grid[0].length - 1);
  }
}