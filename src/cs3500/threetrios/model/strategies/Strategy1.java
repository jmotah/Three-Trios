package cs3500.threetrios.model.strategies;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.GridTile;

//Go for flipping highest score
public class Strategy1 extends AbstractStrategies implements Strategies {

  ReadonlyThreeTriosModel model;

  public Strategy1(ReadonlyThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public HashMap<Point, Integer> runStrategies() {
    return getBestScorePositionForAllCardsInHand();
  }

  private HashMap<Point, Integer> getBestScorePositionForAllCardsInHand() {
    int highestScore = 0;
    Point highestScorePosition = null;
    int bestScoreCardIdxInHand = 0;
    HashMap<Point, Integer> bestPositionAndCardIdx = new HashMap<>();
    //INVARIANCE: Only one item can be placed within the bestPositionAndCardIdx HashMap object

    List<HashMap<Point, Integer>> allPossibilities =
            emulateBattleToFindScoreForAllCardsInAllPossibleSpaces();
    //every index within a list corresponds to a card idx (ex. list index of 0 is card idx of 0)

    bestPositionAndCardIdx.put(null, null);

    for (int i = 0; i < allPossibilities.size(); i++) {
      int compareScore = getBestScore(allPossibilities.get(i));
      Point comparePosition = getBestScorePosition(allPossibilities.get(i));

      if (highestScore == compareScore) {
        Point comparisonDonePosition = comparePositions(highestScorePosition, comparePosition);

        if (comparePosition == comparisonDonePosition) {
          bestPositionAndCardIdx.remove(highestScorePosition);
          highestScorePosition = comparisonDonePosition;
          bestScoreCardIdxInHand = i;
          bestPositionAndCardIdx.put(highestScorePosition, bestScoreCardIdxInHand);
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
    List<Point> keys = getAllPossibleMoves(model);

    for (int i = 0; i < possibleMovesForACard.size(); i++) {
      int compareValue = possibleMovesForACard.get(keys.get(i));
      Point comparePosition = keys.get(i);

      if (highestScore == compareValue) {

        highestScorePosition = comparePositions(highestScorePosition, comparePosition);
      } else if (highestScore < compareValue) {
        highestScorePosition = comparePosition;
      }
      highestScore = Math.max(compareValue, highestScore);
    }
    return highestScorePosition;
  }

  /**
   * Gets the best score from a given HashMap objects of positions and scores.
   *
   * @param possibleMovesForACard a HashMap object of Points and Integers where Point objects are
   *                              the positions on a grid and Integers are the number of cards that
   *                              are flipped if you place a card there
   * @return the best score from a given HashMap object of Points and Integers
   */
  private int getBestScore(HashMap<Point, Integer> possibleMovesForACard) {
    if (possibleMovesForACard == null) {
      throw new IllegalArgumentException("Given hash map object for all possible moves for a card" +
              "is null!");
    }

    return possibleMovesForACard.get(getBestScorePosition(possibleMovesForACard));
  }

  /**
   * Emulates battle to find the score for all cards in a player's hand for all possible
   * moves/spaces to play to.
   *
   * @return return a list of HashMap objects of type Point (key) and Integer (value) where each
   * Point object represents the position of a tile on a grid and each Integer object represents
   * the number of cards that would be flipped if the specified card was played to that position.
   * Each index within the list represents the index of the card emulating battle with (ex. the
   * 0th index HashMap object within the list represents the positions and scores of emulating battle
   * with the 0th card index in the player's hand; the 1st index HashMap object is for the 1st card
   * index in the player's hand and so on
   */
  private java.util.List<HashMap<Point, Integer>> emulateBattleToFindScoreForAllCardsInAllPossibleSpaces() {
    java.util.List<PlayingCard> hand = model.getCurrentTurnPlayer().getHand();
    java.util.List<HashMap<Point, Integer>> allPossibleMovesWithScores = new ArrayList<>();

    for (int card = 0; card < hand.size(); card++) {
      allPossibleMovesWithScores.add(
              emulateBattleToFindScoreForOneCardInAllPossibleSpaces(card));
    }
    return allPossibleMovesWithScores;
  }

  /**
   * Emulate battling to find the score for a single card in a player's hand for all possible
   * moves/spaces to play to.
   *
   * @param cardIdxInHand the card in the player's hand to emulate battle for
   * @return returns a HashMap object of type Point (key) and Integer (value) where each Point
   * object represents the position of a tile on a grid and each Integer object represents the
   * number of cards that would be flipped if the specified card was played to that position.
   */
  private HashMap<Point, Integer> emulateBattleToFindScoreForOneCardInAllPossibleSpaces(
          int cardIdxInHand) {
    if (cardIdxInHand < 0 || cardIdxInHand >= model.getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("Given card index out of bounds!");
    }

    final GridTile[][] gridStartCopy = model.getGrid();
    GridTile[][] grid = getGridCopy(gridStartCopy);
    HashMap<Point, Integer> pointsAndScoresOfCard = new HashMap<>();

    for (int row = 0; row < grid.length; row++) {
      for (int column = 0; column < grid[0].length; column++) {
        if (grid[row][column].getCellType() == CellType.CARD_CELL) {
          int score = emulateBattleToFindScore(row, column, cardIdxInHand, grid);
          pointsAndScoresOfCard.put(new Point(row, column), score);
          grid = getGridCopy(gridStartCopy);
        }
      }
    }
    return pointsAndScoresOfCard;
  }

  /**
   * Plays the given card index in the player's hand to the desired row and column index on the
   * given grid. Battles the nearby cards at the provided row and column index. If the provided
   * card wins the battle, updates the card battled against to become the current player's card.
   * This battling occurs on a copy of a grid, so it does not take place on the true game grid.
   *
   * @param row           the desired row index to initiate the battle with; number is 0-index based
   * @param column        the desired column index to initiate the battle with; number is 0-index based
   * @param cardIdxInHand the index of the card in the player's hand to place at the desired
   *                      row and column index
   * @param grid          the grid to perform the battling on
   * @return an integer representing the number of cards flipped due to battling
   */
  private int emulateBattleToFindScore(int row, int column, int cardIdxInHand,
                                       GridTile[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    } else if (row < 0 || row >= grid.length ||
            column < 0 || column >= grid[0].length) {
      throw new IllegalArgumentException("Row and/or column index out of bounds!");
    } else if (cardIdxInHand < 0 || cardIdxInHand >= model.getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("Given card index out of bounds!");
    }

    int score = 0;

    //places the card on the copy of the grid (playToGrid)
    grid[row][column] = new GridTile(CellType.PLAYER_CELL,
            model.getCurrentTurnPlayer().getHand().get(cardIdxInHand),
            model.getCurrentTurnPlayer());

    return score + battleAllDirections(row, column, grid);
  }

  /**
   * Starts the battle in all four compass directions including north, south, east, and west from
   * the specified tile in the grid. This battling occurs on a copy of a grid, so it does not take
   * place on the true game grid.
   *
   * @param row    the row index where the battle starts from; number is 0-index based
   * @param column the column index where the battle starts from; number is 0-index based
   * @param grid   the grid to perform the battling on
   * @return an integer representing the number of cards flipped due to battling
   */
  private int battleAllDirections(int row, int column, GridTile[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    } else if (row < 0 || row >= grid.length ||
            column < 0 || column >= grid[0].length) {
      throw new IllegalArgumentException("Row and/or column index out of bounds!");
    }

    int score = 0;

    GridTile placedTile = grid[row][column];

    score += battleSpecificDirection(placedTile, row - 1, column, CardCompass.NORTH_VALUE, grid);
    score += battleSpecificDirection(placedTile, row + 1, column, CardCompass.SOUTH_VALUE, grid);
    score += battleSpecificDirection(placedTile, row, column + 1, CardCompass.EAST_VALUE, grid);
    score += battleSpecificDirection(placedTile, row, column - 1, CardCompass.WEST_VALUE, grid);
    return score;
  }

  /**
   * Executes a battle in a specific direction from the given GridTile object. Compares the
   * GridTile object with the object in the specified row and column. This battling occurs on a
   * copy of a grid, so it does not take place on the true game grid.
   *
   * @param current          the object to execute the battle with
   * @param row              the row of the object to compare with the current GridTile object with;
   *                         number is 0-index based
   * @param column           the column of the object to compare with the current GridTile object
   *                         with; number is 0-index based
   * @param compareDirection the direction of comparison
   * @param grid             the grid to perform the battling on
   * @return an integer representing the number of cards flipped due to battling
   */
  private int battleSpecificDirection(GridTile current, int row, int column,
                                      CardCompass compareDirection, GridTile[][] grid) {
    if (current == null) {
      throw new IllegalArgumentException("The provided GridTile object is null!");
    } else if (compareDirection == null) {
      throw new IllegalArgumentException("The provided CardCompass object is null!");
    } else if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    }

    int score = 0;
    if (row >= 0 && row < grid.length &&
            column >= 0 && column < grid[0].length &&
            grid[row][column].getCellType() == CellType.PLAYER_CELL) {
      GridTile compareToTile = grid[row][column];

      if (current.compareTo(compareToTile, compareDirection) == current &&
              compareToTile.getWhichPlayersTile() != model.getCurrentTurnPlayer()) {
        grid[row][column] = new GridTile(compareToTile.getCellType(),
                compareToTile.getPlayingCard(),
                model.getCurrentTurnPlayer());
        score += battleAllDirections(row, column, grid);
        return score + 1;
      }
    }
    return score;
  }

  /**
   * Gets a copy of the given GridTile 2D array object.
   *
   * @param grid the GridTile 2D array object to get a copy of
   * @return a copy of the GridTile 2D array object
   */
  private GridTile[][] getGridCopy(GridTile[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    }

    GridTile[][] copy = new GridTile[grid.length][grid[0].length];
    for (int row = 0; row < grid.length; row++) {
      System.arraycopy(grid[row], 0, copy[row], 0, grid[0].length);
    }
    return copy;
  }
}
