package cs3500.threetrios.controller;

/**
 * Represents features that the controller manages to interact with the game. These methods permit
 * players to perform features of the game through actions like selecting a card at a specific
 * index or selecting a grid cell at a specific row and column.
 */
public interface Features {
  /**
   * Given a card index, selects the respective card in the player's hand.
   *
   * @param cardIndex the card index of the card to select
   */
  void selectCard(int cardIndex);

  /**
   * Given a row and column index, selects the respective grid tile in the game grid and places the
   * selected card index there. The requirement to use this method is that there must be a selected
   * card index already.
   *
   * @param row    the row index to play to
   * @param column the column index to play to
   */
  void selectGridCell(int row, int column);
}