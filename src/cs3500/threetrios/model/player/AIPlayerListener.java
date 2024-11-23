package cs3500.threetrios.model.player;

/**
 * Represents a listener for AI players in the Three Trios game. The listener is responsible for
 * performing the AI player's next turn after the previous player plays. This turn is triggered
 * by the model.
 */
public interface AIPlayerListener {

  void performTurn(PlayerColor color);

}
