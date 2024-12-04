package cs3500.threetrios.providers.model.object_adapters;

import java.util.List;

import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.ReadonlyThreeTriosModel;

/**
 * Adapts the project's main ReadonlyThreeTriosModel interface and class implementation to the
 * provider's ReadonlyThreeTriosModel interface. Adapts the primary implementation to a provider's
 * implementation.
 */
public class MainModelToProviderReadonlyThreeTrios implements ReadonlyThreeTriosModel {

  private final cs3500.threetrios.model.ReadonlyThreeTriosModel model;

  /**
   * MainModelToProviderReadonlyThreeTrios class constructor.
   *
   * @param model the main ReadonlyThreeTriosModel interface implementation class to delegate to
   */
  public MainModelToProviderReadonlyThreeTrios(
          cs3500.threetrios.model.ReadonlyThreeTriosModel model) {

    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
  }

  /**
   * Gets the current grid of the game by delegating.
   *
   * @return the grid representing the game state.
   */
  @Override
  public Grid getGrid() {
    return new MainGridToProviderGrid(model.getGrid());
  }

  /**
   * Gets the current player by delegating.
   *
   * @return the player whose turn it is.
   */
  @Override
  public Player getCurrentPlayer() {
    return new MainHumanToProviderHuman(model.getCurrentTurnPlayer());
  }

  /**
   * Gets the player who is not currently taking their turn by delegating.
   *
   * @return the player whose turn it is not, aka the opponent of the current player.
   */
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

  /**
   * Checks if the game is over by delegating.
   *
   * @return true if the game is over, false otherwise.
   */
  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  /**
   * Gets the contents of a cell in the grid at a given position by delegating.
   *
   * @param row the row of the cell.
   * @param col the column of the cell.
   * @return the contents of the cell at the given position.
   */
  @Override
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= model.getGrid().length ||
            col < 0 || col >= model.getGrid()[0].length) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    return new MainGridTileToProviderCell(model.getGrid()[row][col]);
  }

  /**
   * Checks if the current player can legally play a card at the given coordinates by delegating.
   *
   * @param row the row of the cell.
   * @param col the column of the cell.
   * @return true if the current player can place a card at the specified cell, false otherwise.
   */
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

  /**
   * Gets the score of a player, which is the number of cards they own in their hand
   * plus the number of cards they own on the grid by delegating.
   *
   * @param player the player whose score to retrieve.
   * @return the score of the player.
   */
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

  /**
   * Gets the list of cards in a player's hand by delegating.
   *
   * @param player the player whose hand to retrieve.
   * @return the list of cards in the player's hand.
   */
  @Override
  public List<Card> getPlayerHand(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }

    return player.getHand();
  }

  /**
   * Determines the winner of the game by delegating.
   *
   * @return the Player who has won, or null if there is no winner yet.
   */
  @Override
  public Player determineWinner() {
    return new MainHumanToProviderHuman(model.findWinningPlayer());
  }

  /**
   * Finds the number of cells that have placed cards on them within the grid.
   *
   * @param grid the grid object to look at
   * @return the number of cells that have placed cards on them within the grid
   */
  private int findNumberOfPlacedCardsOnGrid(Grid grid) {
    if (grid == null) {
      throw new IllegalArgumentException("grid cannot be null");
    }

    int placedCardCount = 0;

    for (int row = 0; row < grid.getRows(); row++) {
      for (int column = 0; column < grid.getCols(); column++) {
        if (!grid.getCell(row, column).isHole() &&
                grid.getCell(row, column).getCard() != null) {
          placedCardCount++;
        }
      }
    }
    return placedCardCount;
  }
}