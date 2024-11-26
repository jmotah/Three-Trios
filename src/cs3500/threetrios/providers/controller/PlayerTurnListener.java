package cs3500.threetrios.providers.controller;

import cs3500.threetrios.providers.model.PlayerColor;

/**
 * This interface defines the contract for listening to player turn events in the game.
 * Implementing classes are notified when it is a player's turn to act in the game.
 */
public interface PlayerTurnListener {

  /**
   * Called when it is the specified player's turn to play.
   * Implementing classes should define the actions to take when a player's turn is triggered.
   *
   * @param player The color of the player whose turn it is (either RED or BLUE).
   */
  void onTurn(PlayerColor player);
}
