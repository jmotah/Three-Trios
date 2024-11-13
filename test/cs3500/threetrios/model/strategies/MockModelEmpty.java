package cs3500.threetrios.model.strategies;

import java.io.File;
import java.util.List;

import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.GamePhase;
import cs3500.threetrios.model.GameState;
import cs3500.threetrios.model.PlayerPlayerModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;

public class MockModelEmpty implements ThreeTriosModel {

  ThreeTriosModel model;

  public MockModelEmpty() {
    model = new PlayerPlayerModel();
  }

  @Override
  public void startGame(File cardConfig, File gridConfig) {
    model.startGame(cardConfig, gridConfig);
  }

  @Override
  public void playToGrid(int row, int column, int cardIdxInHand) {
    model.playToGrid(row, column, cardIdxInHand);
  }

  @Override
  public void battle(int row, int column) {

  }


  @Override
  public void updatePlayerTurn() {

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
}