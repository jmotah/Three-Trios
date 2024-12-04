package cs3500.threetrios.model.player;

/**
 * Represents a listener for AI players in the Three Trios game. The listener is responsible for
 * performing the AI player's next turn after the previous player plays. This turn is triggered
 * by the model.
 */
public interface AIPlayerListener {

  /**
   * Performs the AI player's turn. After ensuring it is this player's turn, calculates the best
   * card index and position to play to according to the AIPlayer object's associated strategy.
   *
   * @param color the color of the current player's turn
   */
  void performTurn(PlayerColor color);

}
