package cs3500.threetrios.model.strategies;

import java.awt.*;
import java.util.HashMap;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;

public class Strategy1And2 extends AbstractStrategies implements Strategies {
  ReadonlyThreeTriosModel model;
  Strategy1 strategy1;
  Strategy2 strategy2;

  public Strategy1And2(ReadonlyThreeTriosModel model) {
    this.model = model;
    this.strategy1 = new Strategy1(model);
    this.strategy2 = new Strategy2(model);
  }

  @Override
  public HashMap<Point, Integer> runStrategies() {
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

    java.util.List<HashMap<Point, Integer>> allPossibilities =
            strategy1.emulateBattleToFindScoreForAllCardsInAllPossibleSpaces();
    //every index within a list corresponds to a card idx (ex. list index of 0 is card idx of 0)

    bestPositionAndCardIdx.put(null, null);

    for (int i = 0; i < allPossibilities.size(); i++) {
      int compareScore = strategy1.getBestScore(allPossibilities.get(i));
      Point comparePosition = strategy1.getBestScorePosition(allPossibilities.get(i));

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
}