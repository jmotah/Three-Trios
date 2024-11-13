package cs3500.threetrios.model.strategies;

import java.awt.*;
import java.util.List;
import java.util.HashMap;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * A combination of strategies 1 and 2. Priority is placed on finding the position on the grid that
 * will result in the most number of cards flipped. Then, it analyzes if one of those spots is a
 * corner or not. If it is places it in the corner, if it is not places it in the uppermost
 * leftmost cell. If there are two corners, places the card in the uppermost leftmost corner. If
 * there are no corners, places the card in the uppermost leftmost grid cell. The card closest to
 * index 0 that satisfies the greatest number of card flips will be played in the found position.
 * THIS IS EXTRA CREDIT.
 */
public class Strategy1And2 extends AbstractStrategies implements Strategies {
  ReadonlyThreeTriosModel model;
  Strategy1 strategy1;
  Strategy2 strategy2;

  /**
   * Constructor for Strategy1And2 class.
   *
   * @param model a read-only version of the model to gain access to immutable data
   */
  public Strategy1And2(ReadonlyThreeTriosModel model) {
    this.model = model;
    this.strategy1 = new Strategy1(model);
    this.strategy2 = new Strategy2(model);
  }

  /**
   * Runs the implementation for strategy 1 and 2 and looks for the best position as well as card to
   * play at that position. The Point object in the HashMap represents the position on the grid that
   * could result in the greatest number of cards flipped while the Integer object in the hashmap
   * represents the card index from the current player's hand that would flip the most number of
   * cards on the best grid position.
   *
   * @return a HashMap object of a Point object and an Integer object where the Point object
   * represents a tile on the grid to play the card at and the Integer object represents the card
   * index to play from the current player's hand on that grid tile
   */
  @Override
  public HashMap<Point, Integer> runStrategy() {
    return getBestScorePositionForAllCardsInHandWithCornerConsideration();
  }

  /**
   * Gets a HashMap objects of a Point object and an Integer object representing a grid cell
   * position and card index in the players hand, respectively. This position and card index is
   * what card to place at which grid tile position to yield the most number of cards flipped after
   * battling.
   *
   * @return a HashMap objects of a Point object and an Integer object representing a grid cell
   * position and card index in the players hand, respectively. INVARIANCE: The returned HashMap
   * object only contains one item within it which contains the best position to play at with the
   * best card to play at that position
   */
  private HashMap<Point, Integer> getBestScorePositionForAllCardsInHandWithCornerConsideration() {
    int highestScore = 0;
    Point highestScorePosition = null;
    int bestScoreCardIdxInHand = 0;
    HashMap<Point, Integer> bestPositionAndCardIdx = new HashMap<>();
    //INVARIANCE: Only one item can be placed within the bestPositionAndCardIdx HashMap object

    List<HashMap<Point, Integer>> allPossibilities =
            strategy1.emulateBattleToFindScoreForAllCardsInAllPossibleSpaces();
    System.out.println(allPossibilities);
    //every index within a list corresponds to a card idx (ex. list index of 0 is card idx of 0)

    bestPositionAndCardIdx.put(null, null);

    for (int i = 0; i < allPossibilities.size(); i++) {
      int compareScore = strategy1.getBestScore(allPossibilities.get(i));
      Point comparePosition = getBestScorePosition(allPossibilities.get(i));

      if (highestScore == compareScore) {
        if (strategy2.isACornerPosition(highestScorePosition) && strategy2.isACornerPosition(comparePosition)) {
        } else if (strategy2.isACornerPosition(highestScorePosition)) {
          //State should stay the same; the same card is the current best score and position
        } else if (strategy2.isACornerPosition(comparePosition)) {
          bestPositionAndCardIdx.remove(highestScorePosition);
          highestScorePosition = comparePosition;
          bestScoreCardIdxInHand = i;
          bestPositionAndCardIdx.put(highestScorePosition, bestScoreCardIdxInHand);
        } else {
          Point comparisonDonePosition = comparePositions(highestScorePosition, comparePosition);

          if (comparePosition == comparisonDonePosition) {
            bestPositionAndCardIdx.remove(highestScorePosition);
            highestScorePosition = comparisonDonePosition;
            bestScoreCardIdxInHand = i;
            bestPositionAndCardIdx.put(highestScorePosition, bestScoreCardIdxInHand);
          }
        }
      } else if (highestScore < compareScore) {
        bestPositionAndCardIdx.remove(highestScorePosition);
        highestScore = compareScore;
        highestScorePosition = comparePosition;
        bestScoreCardIdxInHand = i;
        bestPositionAndCardIdx.put(highestScorePosition, bestScoreCardIdxInHand);
      }
    }
    return bestPositionAndCardIdx;
  }

  /**
   * Gets the Point object at which would yield the most number of cards flipped after battling
   * from a given HashMap of objects of positions and scores.
   *
   * @param possibleMovesForACard a HashMap object of Points and Integers where Point objects are
   *                              the positions on a grid and Integers are the number of cards that
   *                              are flipped if you place a card there
   * @return the best position to play to get the highest score from a given HashMap of objects of
   * Points and Integers
   */
  private Point getBestScorePosition(HashMap<Point, Integer> possibleMovesForACard) {
    if (possibleMovesForACard == null) {
      throw new IllegalArgumentException("Given hash map object for all possible moves for a card" +
              "is null!");
    }

    int highestScore = 0;
    Point highestScorePosition = null;
    List<Point> keys = strategy1.getAllPossibleMoves(model);

    for (int i = 0; i < possibleMovesForACard.size(); i++) {
      int compareValue = possibleMovesForACard.get(keys.get(i));
      Point comparePosition = keys.get(i);

      if (highestScore == compareValue) {
        if (strategy2.isACornerPosition(highestScorePosition) && strategy2.isACornerPosition(comparePosition)) {
          highestScorePosition = comparePositions(highestScorePosition, comparePosition);
        } else if (strategy2.isACornerPosition(highestScorePosition)) {
          //State should stay the same; the same card is the current best score and position
        } else if (strategy2.isACornerPosition(comparePosition)) {
          highestScorePosition = comparePosition;
        } else {
          highestScorePosition = comparePositions(highestScorePosition, comparePosition);
        }
      } else if (highestScore < compareValue) {
        highestScorePosition = comparePosition;
      }
      highestScore = Math.max(compareValue, highestScore);
    }
    return highestScorePosition;
  }
}