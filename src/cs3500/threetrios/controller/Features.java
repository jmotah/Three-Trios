package cs3500.threetrios.controller;

/**
 * Represents features that the controller manages to interact with the game. These methods permit
 * players to perform features of the game through actions like selecting a card at a specific
 * index or selecting a grid cell at a specific row and column.
 */
public interface Features {
  void selectCard(int cardIndex);

  void selectGridCell(int row, int column);
}