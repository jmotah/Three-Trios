package cs3500.threetrios.providers.model;

import java.io.File;
import java.util.List;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.providers.controller.ModelStateListener;

public class ProviderModel implements ThreeTriosModel{

  private final GameModel model;
  private final File cardConfig;
  private final File gridConfig;

  public ProviderModel(File cardConfig, File gridConfig) {
    this.model = new GameModel();
    this.cardConfig = cardConfig;
    this.gridConfig = gridConfig;
  }

  /**
   * Places a card at the specified grid location.
   *
   * @param index The index of the card in the player's hand.
   * @param row   The row in the grid where the card will be placed.
   * @param col   The column in the grid where the card will be placed.
   * @throws IllegalArgumentException if the index is out of bounds,
   *                                  or if the specified cell is not a valid move.
   */
  @Override
  public void placeCard(int index, int row, int col) {
    model.playToGrid(row, col, index);
  }

  /**
   * Initializes and starts the game.
   * This method sets up the game state, including shuffling cards,
   * and determining the first player.
   */
  @Override
  public void startGame() {
    model.startGame(cardConfig, gridConfig);
  }

  /**
   * Ends the current player's turn, passing control to the next player.
   * This method should update the game state accordingly.
   */
  @Override
  public void endTurn() {
    model.updatePlayerTurn();
  }

  /**
   * Adds a listener to track changes in the model's state.
   *
   * @param listener the listener to add.
   */
  @Override
  public void addModelStateListener(ModelStateListener listener) {
      return;
  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public Player getCurrentPlayer() {
    return null;
  }

  @Override
  public Player getOtherPlayer() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public Cell getCell(int row, int col) {
    return null;
  }

  @Override
  public boolean canCurrentPlayerPlaceCard(int row, int col) {
    return false;
  }

  @Override
  public int getPlayerScore(Player player) {
    return 0;
  }

  @Override
  public List<Card> getPlayerHand(Player player) {
    return List.of();
  }

  @Override
  public Player determineWinner() {
    return null;
  }
}
