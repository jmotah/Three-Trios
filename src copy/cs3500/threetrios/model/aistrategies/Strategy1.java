package cs3500.threetrios.model.aistrategies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;

/**
 * Implementation of the strategy 1. Strategy 1 is the idea of flipping as many cards as possible
 * for the player's turn, meaning finding the most optimal position to play a card at and then
 * finding the most optimal card to play for that position, if any. If there is a tie for number
 * of cards flipped, positions the card in the uppermost left-most position.
 */
public class Strategy1 extends AbstractStrategies implements Strategies {

  private final ReadonlyThreeTriosModel model;

  /**
   * Constructor for Strategy1 class.
   *
   * @param model a read-only version of the model to gain immutable data from
   */
  public Strategy1(ReadonlyThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    this.model = model;
  }

  /**
   * Runs strategy 1 and looks for the best position as well as card to play at that position. The
   * Point object in the HashMap represents the position on the grid that could result in the
   * greatest number of cards flipped while the Integer object in the hashmap represents the card
   * index from the current player's hand that would flip the most number of cards on the best grid
   * position. If a tie occurs, positions the card in the uppermost left-most position found that
   * can still account for the greatest number of cards flipped.
   *
   * @return a HashMap object of a Point object and an Integer object where the Point object
   *     represents a tile on the grid to play the card at and the Integer object represents the
   *     card index to play from the current player's hand on that grid tile
   */
  @Override
  public HashMap<Point, Integer> runStrategy() {
    return getBestScorePositionForAllCardsInHand();
  }

  /**
   * Gets a HashMap objects of a Point object and an Integer object representing a grid cell
   * position and card index in the players hand, respectively. This position and card index is
   * what card to place at which grid tile position to yield the most number of cards flipped after
   * battling. If a tie occurs, positions the card in the uppermost left-most position found that
   * can still account for the greatest number of cards flipped.
   *
   * @return a HashMap objects of a Point object and an Integer object representing a grid cell
   *     position and card index in the players hand, respectively. INVARIANCE: The returned
   *     HashMap object only contains one item within it which contains the best position to
   *     play at with the best card to play at that position
   */
  private HashMap<Point, Integer> getBestScorePositionForAllCardsInHand() {
    int highestScore = 0;
    Point highestScorePosition = null;
    int bestScoreCardIdxInHand;
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
        Point comparisonDonePosition = comparePositions(highestScorePosition,
                comparePosition);

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
   * from a given HashMap of objects of positions and scores. This accounts for tie scenarios.
   * If a tie occurs, positions the card in the uppermost left-most position found that can still
   * account for the greatest number of cards flipped.
   *
   * @param possibleMovesForACard a HashMap object of Points and Integers where Point objects are
   *                              the positions on a grid and Integers are the number of cards that
   *                              are flipped if you place a card there
   * @return the best position to play to get the highest score from a given HashMap of objects of
   *     Points and Integers
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

        highestScorePosition = comparePositions(highestScorePosition,
                comparePosition);
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
  protected int getBestScore(HashMap<Point, Integer> possibleMovesForACard) {
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
   *     Point object represents the position of a tile on a grid and each Integer
   *     object represents the number of cards that would be flipped if the specified card
   *     was played to that position. Each index within the list represents the index of the
   *     card emulating battle with (ex. the 0th index HashMap object within the list
   *     represents the positions and scores of emulating battle with the 0th card index
   *     in the player's hand; the 1st index HashMap object is for the 1st card index in the
   *     player's hand and so on
   */
  protected List<HashMap<Point, Integer>>
      emulateBattleToFindScoreForAllCardsInAllPossibleSpaces() {
    List<Cards> hand = model.getCurrentTurnPlayer().getHand();
    List<HashMap<Point, Integer>> allPossibleMovesWithScores = new ArrayList<>();

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
   *     object represents the position of a tile on a grid and each Integer object
   *     represents the number of cards that would be flipped if the specified card was played
   *     to that position. This method must be public for the hints decorator to function properly.
   *     It only delivers immutable data, nothing mutable.
   */
  public HashMap<Point, Integer> emulateBattleToFindScoreForOneCardInAllPossibleSpaces(
          int cardIdxInHand) {
    if (cardIdxInHand < 0 || cardIdxInHand >= model.getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("Given card index out of bounds!");
    }

    final Grid[][] gridStartCopy = model.getGrid();
    Grid[][] grid = getGridCopy(gridStartCopy);
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
   * @param column        the desired column index to initiate the battle with; number is 0-index
   *                      based
   * @param cardIdxInHand the index of the card in the player's hand to place at the desired
   *                      row and column index
   * @param grid          the grid to perform the battling on
   * @return an integer representing the number of cards flipped due to battling
   */
  private int emulateBattleToFindScore(int row, int column, int cardIdxInHand,
                                       Grid[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    } else if (row < 0 || row >= grid.length ||
            column < 0 || column >= grid[0].length) {
      throw new IllegalArgumentException("Row and/or column index out of bounds!");
    } else if (cardIdxInHand < 0 ||
            cardIdxInHand >= model.getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("Given card index out of bounds!");
    }

    int score = 0;

    //places the card on the copy of the grid (playToGrid)
    grid[row][column] = new GridTile(CellType.PLAYER_CELL,
            model.getCurrentTurnPlayer().getHand().get(cardIdxInHand),
            model.getCurrentTurnPlayer());

    score += battleForRulePreCombo(
            model.getBattleRule().applyRule(row, column, grid),
            grid);

    return score + battleAllDirections(row, column, grid);
  }

  /**
   * Applies a battle rule pre-combo to a set of tiles to help compute the total score of flippable
   * tiles when a battle rule is put into placed.
   *
   * @param pointsToBattleWith the list of points representation the row and column indices of the
   *                           tiles to be flipped for the battle rule
   * @param grid               a copy of the game grid
   * @return the integer of the total scored gained by the player applying the battle rule
   */
  private int battleForRulePreCombo(List<Point> pointsToBattleWith, Grid[][] grid) {
    if (pointsToBattleWith == null || pointsToBattleWith.size() < 2) {
      return 0;
    } else if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    }

    int score = 0;

    for (Point tileCoordinates : pointsToBattleWith) {
      int row = (int) tileCoordinates.getX();
      int column = (int) tileCoordinates.getY();

      Grid tile = grid[row][column];

      if (tile.getCellType() == CellType.PLAYER_CELL &&
              tile.getWhichPlayersTile() != model.getCurrentTurnPlayer()) {
        grid[row][column] = new GridTile(tile.getCellType(),
                tile.getPlayingCard(),
                model.getCurrentTurnPlayer());
        score += 1;
        score += battleAllDirections(row, column, grid);
      }
    }
    return score;
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
  private int battleAllDirections(int row, int column, Grid[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    } else if (row < 0 || row >= grid.length ||
            column < 0 || column >= grid[0].length) {
      throw new IllegalArgumentException("Row and/or column index out of bounds!");
    }

    int score = 0;

    Grid placedTile = grid[row][column];

    score += battleSpecificDirection(placedTile, row - 1, column,
            CardCompass.NORTH_VALUE, grid);
    score += battleSpecificDirection(placedTile, row + 1, column,
            CardCompass.SOUTH_VALUE, grid);
    score += battleSpecificDirection(placedTile, row, column + 1,
            CardCompass.EAST_VALUE, grid);
    score += battleSpecificDirection(placedTile, row, column - 1,
            CardCompass.WEST_VALUE, grid);
    return score;
  }

  /**
   * Executes a battle in a specific direction from the given GridTile object. Compares the
   * GridTile object with the object in the specified row and column. This battling occurs on a
   * copy of a grid, so it does not take place on the true game grid.
   *
   * @param attackerTile     the object to execute the battle with
   * @param row              the row of the object to compare with the current GridTile object with;
   *                         number is 0-index based
   * @param column           the column of the object to compare with the current GridTile object
   *                         with; number is 0-index based
   * @param compareDirection the direction of comparison
   * @param grid             the grid to perform the battling on
   * @return an integer representing the number of cards flipped due to battling
   */
  private int battleSpecificDirection(Grid attackerTile, int row, int column,
                                      CardCompass compareDirection, Grid[][] grid) {
    if (attackerTile == null) {
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
      Grid defenderTile = grid[row][column];

      if (canAttackerAttack(attackerTile, defenderTile, compareDirection) &&
              defenderTile.getWhichPlayersTile() != model.getCurrentTurnPlayer()) {
        grid[row][column] = new GridTile(defenderTile.getCellType(),
                defenderTile.getPlayingCard(),
                model.getCurrentTurnPlayer());
        score += battleAllDirections(row, column, grid);
        return score + 1;
      }
    }
    return score;
  }

  private boolean canAttackerAttack(Grid attackerTile, Grid defenderTile,
                                    CardCompass comparisonDirection) {

    CardCompass oppositeDirection = comparisonDirection.oppositeDirection();

    int attackerDirectionalValue =
            attackerTile.getPlayingCard().getValue(comparisonDirection);
    int defenderOppositeDirectionalValue =
            defenderTile.getPlayingCard().getValue(oppositeDirection);

    return model.getBattleStrategy().shouldCardFlip(attackerDirectionalValue,
            defenderOppositeDirectionalValue);
  }

  /**
   * Gets a copy of the given GridTile 2D array object.
   *
   * @param grid the GridTile 2D array object to get a copy of
   * @return a copy of the GridTile 2D array object
   */
  private Grid[][] getGridCopy(Grid[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Given grid is null!");
    }

    Grid[][] copy = new GridTile[grid.length][grid[0].length];
    for (int row = 0; row < grid.length; row++) {
      System.arraycopy(grid[row], 0, copy[row], 0, grid[0].length);
    }
    return copy;
  }
}