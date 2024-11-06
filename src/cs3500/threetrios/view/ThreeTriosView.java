package cs3500.threetrios.view;

/**
 * A generic view for the Three Trios game which is in charge of displaying the game grid, player
 * hand(s), and current player's turn.
 **/
public interface ThreeTriosView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.)
   */
  void render();
}