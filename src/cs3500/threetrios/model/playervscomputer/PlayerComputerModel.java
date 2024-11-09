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

  private List<Integer> analyzeAllPossibleMovesForEveryCardInHand() {
    List<Point> allPossibleGridMoves = this.getAllPossibleMoves();
    List<Integer> bestScoresOfAllMoves = new ArrayList<>();

    for (int numOfMoves = 0; numOfMoves < allPossibleGridMoves.size(); numOfMoves++) {
//      allPossibleGridMoves.add(analyzeAllPossibleMovesAndGetBestScore());
    }
    return List.of();
  }

  private int analyzeAllPossibleMovesAndGetBestScore() {
    //return this.emulateBattleToFindScore(0, 0, 0); //change
    return 0;
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

  private HashMap<Point, Integer> emulateBattleToFindScoreForOneCardInAllPossibleSpaces(int cardIdxInHand) {
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
}