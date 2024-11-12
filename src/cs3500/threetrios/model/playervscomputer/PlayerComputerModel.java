package cs3500.threetrios.model.playervscomputer;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.playervsplayer.PlayerPlayerModel;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.player.PlayerColor;

/**
 * Represents the model for the ThreeTrios card game. Manages starting the game as well as
 * initializing the hands and the grid. This model is specifically for a Player vs. AI/Computer
 * experience.
 */
public class PlayerComputerModel extends PlayerPlayerModel implements ThreeTriosModel {

  private final PlayerPlayerModel playerModel;

  /**
   * Constructor for the PlayerComputerModel class.
   */
  public PlayerComputerModel() {
    this.playerModel = new PlayerPlayerModel();
  }

  /**
   * Starts the game with a given set of configurations from a card and grid configuration file.
   *
   * @param cardConfig the desired card configuration file
   * @param gridConfig the desired grid configuration file
   */
  @Override
  public void startGame(File cardConfig, File gridConfig) {
    playerModel.startGame(cardConfig, gridConfig);
  }

  /**
   * Plays a desired card from the current player's hand to a desired cell in the grid.
   *
   * @param row           the desired row to play to; number is 0-index based
   * @param column        the desired column to play to; number is 0-index based
   * @param cardIdxInHand the desired card index to play from the player's hand; number is 0-index
   *                      based
   */
  @Override
  public void playToGrid(int row, int column, int cardIdxInHand) {
    playerModel.playToGrid(row, column, cardIdxInHand);
  }

  /**
   * Battles the nearby cards at the provided row and column index. If the provided card wins the
   * battle, updates the card battled against to become the current player's card.
   *
   * @param row    the desired row index to initiate the battle with; number is 0-index based
   * @param column the desired column index to initiate the battle with; number is 0-index based
   */
  @Override
  public void battle(int row, int column) {
    playerModel.battle(row, column);
  }

  /**
   * Updates the current player's turn from the given player to the next player. As an example,
   * if the given player is RED, the current player's turn will update to BLUE, and vice versa.
   */
  @Override
  public void updatePlayerTurn() {
    playerModel.updatePlayerTurn();
  }

  /**
   * Checks if the game is over or not. The game is over if every tile in the grid has a valid
   * playing card placed in it.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return playerModel.isGameOver();
  }

  /**
   * Finds the current winning player in the game. The winning player is chosen by which player
   * has the greatest number of cards in that player's name. This includes cards on the grid, and
   * cards in the player's hand. If there is a tie, null is returned.
   *
   * @return the winning player as a Players object
   */
  @Override
  public Players findWinningPlayer() {
    return playerModel.findWinningPlayer();
  }

  /**
   * Gets the current game grid.
   *
   * @return a copy of the game grid
   */
  @Override
  public GridTile[][] getGrid() {
    return playerModel.getGrid();
  }

  /**
   * Gets the current player's turn.
   *
   * @return the current player's turn
   */
  @Override
  public Players getCurrentTurnPlayer() {
    return playerModel.getCurrentTurnPlayer();
  }

  /**
   * Given a player color, yields the respective Player object.
   *
   * @param color the player color of the player to return
   * @return the found Player object
   */
  @Override
  public Players getPlayerOfColor(PlayerColor color) {
    return playerModel.getPlayerOfColor(color);
  }

  /**
   * Calculates the most optimal move to play to the grid to result in the most number of cards
   * flipped after battling. Plays the most optimal card to the most optimal grid position while
   * also performing battling. The AI plays for whichever player's current turn it is.
   */
  @Override
  public void playToGridAI() {
    HashMap<Point, Integer> bestPositionAndCardIdx =
            getBestScorePositionForAllCardsInHand();

    Point position = bestPositionAndCardIdx.keySet().iterator().next();
    int cardIdx = bestPositionAndCardIdx.get(position);

    this.playToGrid((int) position.getX(), (int) position.getY(), cardIdx);
    this.battle((int) position.getX(), (int) position.getY());
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
        if (isACornerPosition(highestScorePosition) && isACornerPosition(comparePosition)) {

        } else if (isACornerPosition(highestScorePosition)) {
          //State should stay the same; the same card is the current best score and position
        } else if (isACornerPosition(comparePosition)) {
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
   * Checks to see if the position is the corner position on the game grid.
   *
   * @param position the position on the grid; a Point object represents the position on the game
   *                 grid
   * @return true if the Point object is a corner position, false otherwise
   */
  private boolean isACornerPosition(Point position) {
    GridTile[][] grid = this.getGrid();

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
    List<Point> keys = getAllPossibleMoves();

    for (int i = 0; i < possibleMovesForACard.size(); i++) {
      int compareValue = possibleMovesForACard.get(keys.get(i));
      Point comparePosition = keys.get(i);

      if (highestScore == compareValue) {
        if (isACornerPosition(highestScorePosition) && isACornerPosition(comparePosition)) {
          highestScorePosition = comparePositions(highestScorePosition, comparePosition);
        } else if (isACornerPosition(highestScorePosition)) {
          //State should stay the same; the same card is the current best score and position
        } else if (isACornerPosition(comparePosition)) {
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
   * Compares two Point objects. Returns the uppermost, left-most Point object. If both
   * Points yield the same x and y values, then it returns the highestScorePoint Point object.
   * Points that are more upper (row closer to 0) take more priority than points that are more
   * left (columns closer to 0).
   *
   * @param highestScorePoint the first Point object
   * @param comparePosition   the second Point object
   * @return returns the uppermost, left-most Point object. If both Point objects yield the same x
   * and y values, then it returns the highestScorePoint Point object.
   */
  private Point comparePositions(Point highestScorePoint, Point comparePosition) {
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
   * same Y value, then it returns the highestScorePoint Point object
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
  private List<HashMap<Point, Integer>> emulateBattleToFindScoreForAllCardsInAllPossibleSpaces() {
    List<PlayingCard> hand = playerModel.getCurrentTurnPlayer().getHand();
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
   * object represents the position of a tile on a grid and each Integer object represents the
   * number of cards that would be flipped if the specified card was played to that position.
   */
  private HashMap<Point, Integer> emulateBattleToFindScoreForOneCardInAllPossibleSpaces(
          int cardIdxInHand) {
    if (cardIdxInHand < 0 || cardIdxInHand >= playerModel.getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("Given card index out of bounds!");
    }

    final GridTile[][] gridStartCopy = this.getGrid();
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
    } else if (cardIdxInHand < 0 || cardIdxInHand >= playerModel.getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("Given card index out of bounds!");
    }

    int score = 0;

    //places the card on the copy of the grid (playToGrid)
    grid[row][column] = new GridTile(CellType.PLAYER_CELL,
            this.getCurrentTurnPlayer().getHand().get(cardIdxInHand),
            this.getCurrentTurnPlayer());

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
              compareToTile.getWhichPlayersTile() != this.getCurrentTurnPlayer()) {
        grid[row][column] = new GridTile(compareToTile.getCellType(),
                compareToTile.getPlayingCard(),
                this.getCurrentTurnPlayer());
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

  /**
   * Gets all the possible moves on the grid.
   *
   * @return returns a list of Point objects. Each Point object represents the position of a
   * tile on a grid.
   */
  private List<Point> getAllPossibleMoves() {
    List<Point> allPossibleGridMoves = new ArrayList<>();
    for (int row = 0; row < this.getGrid().length; row++) {
      for (int column = 0; column < this.getGrid()[0].length; column++) {
        if (this.getGrid()[row][column].getCellType() == CellType.CARD_CELL) {
          allPossibleGridMoves.add(new Point(row, column));
        }
      }
    }
    return allPossibleGridMoves;
  }
}