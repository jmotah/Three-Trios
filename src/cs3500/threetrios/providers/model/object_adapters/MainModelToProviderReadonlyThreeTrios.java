package cs3500.threetrios.providers.model.object_adapters;

import java.util.List;

import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.ReadonlyThreeTriosModel;

public class MainModelToProviderReadonlyThreeTrios implements ReadonlyThreeTriosModel {

  private final cs3500.threetrios.model.ReadonlyThreeTriosModel model;

  public MainModelToProviderReadonlyThreeTrios(
          cs3500.threetrios.model.ReadonlyThreeTriosModel model) {

    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
  }

  @Override
  public Grid getGrid() {
    return new MainGridToProviderGrid(model.getGrid());
  }

  @Override
  public Player getCurrentPlayer() {
    return new MainHumanToProviderHuman(model.getCurrentTurnPlayer());
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
            model.getPlayerOfColor(otherPlayerColor));
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= model.getGrid().length ||
            col < 0 || col >= model.getGrid()[0].length) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    return new MainGridTileToProviderCell(model.getGrid()[row][col]);
  }

  @Override
  public boolean canCurrentPlayerPlaceCard(int row, int col) {
    if (row < 0 || row >= model.getGrid().length ||
            col < 0 || col >= model.getGrid()[0].length) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    CellType cellType = model.getGrid()[row][col].getCellType();

    if (cellType == CellType.HOLE) {
      return true;
    }
    return false;
  }

  @Override
  public int getPlayerScore(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }

    PlayerColor providerPlayerColor = player.getColor();

    if (providerPlayerColor == this.determineWinner().getColor()) {
      return model.findWinningPlayerScore();
    } else {
      return model.findWinningPlayerScore() -
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
    return new MainHumanToProviderHuman(model.findWinningPlayer());
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
}