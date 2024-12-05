package cs3500.threetrios.model.aistrategies;

import java.util.List;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.battlestrategies.BattleStrategies;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.controller.ThreeTriosModelListener;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.AIPlayerListener;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;

/**
 * Represents a mock model with all the cards played in except the four corners.
 */
public class MockModelEmptyCorners implements ThreeTriosModel {

  ThreeTriosModel model;

  public MockModelEmptyCorners() {
    model = new GameModel();
  }

  @Override
  public void startGame(Grid[][] grid, List<Cards> deck) {
    model.startGame(grid, deck);
  }

  @Override
  public void playToGrid(int row, int column, int cardIdxInHand) {
    model.playToGrid(row, column, cardIdxInHand);
  }

  @Override
  public void battle(int row, int column) {
    model.battle(row, column);
  }

  @Override
  public void setBattleRule(BattleStrategies strategy) {
    return;
  }


  @Override
  public void updatePlayerTurn() {
    model.updatePlayerTurn();
  }

  @Override
  public void addViewListener(ThreeTriosModelListener listener) {
    return;
  }

  @Override
  public void addAITurnListener(AIPlayerListener listener) {
    return;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Players findWinningPlayer() {
    return null;
  }

  @Override
  public int findWinningPlayerScore() {
    return 0;
  }

  @Override
  public GridTile[][] getGrid() {
    GridTile[][] grid = new GridTile[3][3];

    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 3; column++) {
        if (row == 0 && column == 0 ||
                row == 3 && column == 0 ||
                row == 0 && column == 3 ||
                row == 3 && column == 3) {
          grid[row][column] = new GridTile(CellType.CARD_CELL, null,
                  null);
        } else {
          grid[row][column] = new GridTile(CellType.PLAYER_CELL,
                  new PlayingCard("One", CardNumbers.ONE, CardNumbers.ONE,
                          CardNumbers.ONE, CardNumbers.ONE),
                  new Player(PlayerColor.RED, List.of()));
        }
      }
    }
    return grid;
  }

  @Override
  public Players getCurrentTurnPlayer() {
    return new Player(PlayerColor.RED, List.of(new PlayingCard("One",
            CardNumbers.ONE, CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE)));
  }

  @Override
  public Players getPlayerOfColor(PlayerColor color) {
    return null;
  }

  @Override
  public BattleStrategies getBattleStrategy() {
    return null;
  }
}