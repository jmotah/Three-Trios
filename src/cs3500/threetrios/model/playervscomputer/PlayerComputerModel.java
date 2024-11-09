package cs3500.threetrios.model.playervscomputer;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.cards.CardCompass;
import cs3500.threetrios.cards.PlayingCard;
import cs3500.threetrios.grid.GridTile;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.playervsplayer.PlayerPlayerModel;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.Player;
import cs3500.threetrios.player.PlayerColor;

public class PlayerComputerModel extends PlayerPlayerModel implements ThreeTriosModel {

  PlayerPlayerModel playerModel;

  public PlayerComputerModel(PlayerPlayerModel model) {
    this.playerModel = model;
  }

  @Override
  public void startGame(File cardConfig, File gridConfig) {
    playerModel.startGame(cardConfig, gridConfig);
  }

  @Override
  public void playToGrid(int row, int column, int cardIdxInHand) {
    playerModel.playToGrid(row, column, cardIdxInHand);
  }

  @Override
  public void battle(int row, int column) {
    playerModel.battle(row, column);
  }

  @Override
  public void updatePlayerTurn() {
    playerModel.updatePlayerTurn();
  }

  @Override
  public boolean isGameOver() {
    return playerModel.isGameOver();
  }

  @Override
  public Player findWinningPlayer() {
    return playerModel.findWinningPlayer();
  }

  @Override
  public GridTile[][] getGrid() {
    return playerModel.getGrid();
  }

  @Override
  public Player getCurrentTurnPlayer() {
    return playerModel.getCurrentTurnPlayer();
  }

  @Override
  public Player getPlayerOfColor(PlayerColor color) {
    return playerModel.getPlayerOfColor(color);
  }

  public void playToGridAI() {
    HashMap<Point, Integer> bestPositionAndCardIdx =
            getBestScorePositionForAllCardsInHand(
                    this.emulateBattleToFindScoreForAllCardsInAllPossibleSpaces());

    Point position = bestPositionAndCardIdx.keySet().iterator().next();
    int cardIdx = bestPositionAndCardIdx.get(position);

    this.playToGrid((int) position.getX(), (int) position.getY(), cardIdx);
    this.battle((int) position.getX(), (int) position.getY());
  }

  public int getBestScoreForAllCardsInHand(List<HashMap<Point, Integer>> allPossibilities) {
    HashMap<Point, Integer> positionAndCardIdx = getBestScorePositionForAllCardsInHand(allPossibilities);

    Point position = positionAndCardIdx.entrySet().iterator().next().getKey();
    int cardIdx = positionAndCardIdx.get(position);

    return getBestScore(allPossibilities.get(cardIdx));
  }

  public HashMap<Point, Integer> getBestScorePositionForAllCardsInHand(List<HashMap<Point, Integer>> allPossibilities) {
    int highestScore = 0;
    Point highestScorePosition = null;
    int bestScoreCardIdxInHand = 0;
    HashMap<Point, Integer> bestPositionAndCardIdx = new HashMap<>();

    bestPositionAndCardIdx.put(null, null);

    for (int i = 0; i < allPossibilities.size(); i++) {
      int compareScore = getBestScore(allPossibilities.get(i));
      Point comparePosition = getBestScorePosition(allPossibilities.get(i));

      if (highestScore == compareScore) {
        Point comparisonDonePosition = comparePositions(highestScorePosition, comparePosition);

        if (comparePosition == comparisonDonePosition) {
          bestPositionAndCardIdx.remove(highestScorePosition);
          bestScoreCardIdxInHand = i;
          bestPositionAndCardIdx.put(comparisonDonePosition, bestScoreCardIdxInHand);
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

  public Point getBestScorePosition(HashMap<Point, Integer> possibilities) {
    int highestScore = 0;
    Point highestScorePosition = null;
    List<Point> keys = getAllPossibleMoves();

    for (int i = 0; i < possibilities.size(); i++) {
      int compareValue = possibilities.get(keys.get(i));
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

  public int getBestScore(HashMap<Point, Integer> possibilities) {
    return possibilities.get(getBestScorePosition(possibilities));
  }

  private Point comparePositions(Point highestScorePoint, Point comparePosition) {
    if (highestScorePoint == null) {
      return comparePosition;
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

  private Point comparePositionsHelper(Point highestScorePoint, Point comparePosition) {
    if (highestScorePoint.getY() == comparePosition.getY()) {
      return highestScorePoint; //this will be the lesser index, so return it
    } else if (highestScorePoint.getY() < comparePosition.getY()) {
      return highestScorePoint;
    } else if (highestScorePoint.getY() > comparePosition.getY()) {
      return comparePosition;
    }
    return highestScorePoint;
  }

  public List<HashMap<Point, Integer>> emulateBattleToFindScoreForAllCardsInAllPossibleSpaces() {
    List<PlayingCard> hand = playerModel.getCurrentTurnPlayer().getHand();
    List<HashMap<Point, Integer>> allPossibleMovesWithScores = new ArrayList<>();

    for (int card = 0; card < hand.size(); card++) {
      allPossibleMovesWithScores.add(
              emulateBattleToFindScoreForOneCardInAllPossibleSpaces(card));
    }
    return allPossibleMovesWithScores;
  }

  public HashMap<Point, Integer> emulateBattleToFindScoreForOneCardInAllPossibleSpaces(
          int cardIdxInHand) {
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

  private int emulateBattleToFindScore(int row, int column, int cardIdxInHand, GridTile[][] grid) {
    int score = 0;

    //places the card on the copy of the grid
    grid[row][column] = new GridTile(CellType.PLAYER_CELL,
            this.getCurrentTurnPlayer().getHand().get(cardIdxInHand),
            this.getCurrentTurnPlayer());

    return score += battleAllDirections(row, column, grid, cardIdxInHand);
  }


  private int battleAllDirections(int row, int column, GridTile[][] grid, int cardIdxInHand) {

    int score = 0;

    GridTile placedTile = grid[row][column];

    score += battleSpecificDirection(placedTile, row - 1, column, CardCompass.NORTH_VALUE, grid);
    score += battleSpecificDirection(placedTile, row + 1, column, CardCompass.SOUTH_VALUE, grid);
    score += battleSpecificDirection(placedTile, row, column + 1, CardCompass.EAST_VALUE, grid);
    score += battleSpecificDirection(placedTile, row, column - 1, CardCompass.WEST_VALUE, grid);
    return score;
  }

  private int battleSpecificDirection(GridTile current, int row, int column,
                                      CardCompass compareDirection, GridTile[][] grid) {
    if (current == null) {
      throw new IllegalArgumentException("The provided GridTile object is null!");
    } else if (compareDirection == null) {
      throw new IllegalArgumentException("The provided CardCompass object is null!");
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
        score += battleAllDirections(row, column, grid, 0);
        return score += 1;
      }
    }
    return score;
  }

  private GridTile[][] getGridCopy(GridTile[][] grid) {
    GridTile[][] copy = new GridTile[grid.length][grid[0].length];
    for (int row = 0; row < grid.length; row++) {
      for (int column = 0; column < grid[0].length; column++) {
        copy[row][column] = grid[row][column];
      }
    }
    return copy;
  }

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