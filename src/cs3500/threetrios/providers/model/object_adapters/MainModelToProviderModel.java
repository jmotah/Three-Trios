package cs3500.threetrios.providers.model.object_adapters;

import java.util.List;

import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.providers.controller.ModelStateListener;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.ThreeTriosModel;

public class MainModelToProviderModel implements ThreeTriosModel {

  private cs3500.threetrios.model.ThreeTriosModel mainModel;

  public MainModelToProviderModel(cs3500.threetrios.model.ThreeTriosModel mainModel) {
    if (mainModel == null) {
      throw new IllegalArgumentException("mainModel cannot be null");
    }

    this.mainModel = mainModel;
  }

  //handles play to grid and battle
  @Override
  public void placeCard(int index, int row, int col) {
    if (row < 0 || row >= mainModel.getGrid().length ||
            col < 0 || col >= mainModel.getGrid()[0].length) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    mainModel.playToGrid(row, col, index);
  }

  @Override
  public void startGame() {
    mainModel.startGame(
            providerGridToMainGrid(),
            null); //HOW CAN I ADAPT START GAME???
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
    if (row < 0 || row >= mainModel.getGrid().length ||
            col < 0 || col >= mainModel.getGrid()[0].length) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    return new MainGridTileToProviderCell(mainModel.getGrid()[row][col]);
  }

  @Override
  public boolean canCurrentPlayerPlaceCard(int row, int col) {
    if (row < 0 || row >= mainModel.getGrid().length ||
            col < 0 || col >= mainModel.getGrid()[0].length) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    Cell cell = getGrid().getCell(row, col);
    return !cell.isHole() && cell.isEmpty();
  }

  @Override
  public int getPlayerScore(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }

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
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }

    return player.getHand();
  }

  @Override
  public Player determineWinner() {
    return new MainHumanToProviderHuman(mainModel.findWinningPlayer());
  }

  private int findNumberOfPlacedCardsOnGrid(Grid grid) {
    if (grid == null) {
      throw new IllegalArgumentException("grid cannot be null");
    }

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

  private cs3500.threetrios.model.grid.Grid[][] providerGridToMainGrid() {
    Grid providerGrid = this.getGrid();
    cs3500.threetrios.model.grid.Grid[][] mainGrid =
            new GridTile[providerGrid.getRows()][providerGrid.getCols()];

    for (int i = 0; i < providerGrid.getRows(); i++) {
      for (int j = 0; j < providerGrid.getCols(); j++) {
        cs3500.threetrios.model.grid.Grid mainGridTile = new ProviderCellToMainGridTile(
                providerGrid.getCell(i, j));
        mainGrid[i][j] = mainGridTile;
      }
    }
    return mainGrid;
  }
}