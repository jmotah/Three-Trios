package cs3500.threetrios.providers.model.object_adapters;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.providers.controller.ModelStateListener;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.ThreeTriosModel;
import cs3500.threetrios.providers.model.enum_adapters.ConvertEnums;

public class MainModelToProviderModel implements ThreeTriosModel {

  cs3500.threetrios.model.ThreeTriosModel mainModel;

  public MainModelToProviderModel(cs3500.threetrios.model.ThreeTriosModel mainModel) {
    this.mainModel = mainModel;
  }

  //handles play to grid and battle
  @Override
  public void placeCard(int index, int row, int col) {
    mainModel.playToGrid(row, col, index);
  }

  @Override
  public void startGame() {
    mainModel.startGame(
            this.getGrid(),
            null);
  }

  @Override
  public void endTurn() {
    mainModel.updatePlayerTurn();
  }

  @Override
  public void addModelStateListener(ModelStateListener listener) {

  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public Player getCurrentPlayer() {
    return new MainHumanToProviderHuman(mainModel.getCurrentTurnPlayer());
  }

  @Override
  public Player getOtherPlayer() {
    cs3500.threetrios.model.player.PlayerColor otherPlayerColor;

    if (this.getCurrentPlayer().getColor() == PlayerColor.BLUE) {
      otherPlayerColor =
              cs3500.threetrios.model.player.PlayerColor.RED;
    } else {
      otherPlayerColor =
              cs3500.threetrios.model.player.PlayerColor.BLUE;
    }

    return new MainHumanToProviderHuman(
            mainModel.getPlayerOfColor(otherPlayerColor));
  }

  @Override
  public boolean isGameOver() {
    return mainModel.isGameOver();
  }

  @Override
  public Cell getCell(int row, int col) {
    return new MainGridTileToProviderCell(mainModel.getGrid()[row][col]);
  }

  @Override
  public boolean canCurrentPlayerPlaceCard(int row, int col) {
    CellType cellType = mainModel.getGrid()[row][col].getCellType();

    if (cellType == CellType.HOLE) {
      return true;
    }
    return false;
  }

  @Override
  public int getPlayerScore(Player player) {
    PlayerColor providerPlayerColor = player.getColor();

    if (providerPlayerColor == this.determineWinner().getColor()) {
      return mainModel.findWinningPlayerScore();
    } else {
      return mainModel.findWinningPlayerScore() -
              findNumberOfPlacedCardsOnGrid(this.getGrid());
    }
  }

  @Override
  public List<Card> getPlayerHand(Player player) {
    return player.getHand();
  }

  @Override
  public Player determineWinner() {
    return new MainHumanToProviderHuman(mainModel.findWinningPlayer());
  }

  private int findNumberOfPlacedCardsOnGrid(Grid grid) {
    int placedCardCount = 0;

    for (int row = 0; row < grid.getRows(); row++) {
      for (int column = 0; column < grid.getCols(); column++) {
        if (!grid.getCell(row, column).isHole()) {
          placedCardCount++;
        }
      }
    }
    return placedCardCount;
  }
}