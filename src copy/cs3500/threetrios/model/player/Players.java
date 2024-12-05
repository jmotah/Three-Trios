package cs3500.threetrios.model.player;

import java.util.List;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.cards.Cards;

/**
 * Represents a type of player which possesses a player color and a hand of PlayingCard objects.
 * This can be an AI.
 */
public interface Players {

  /**
   * Returns the player's color.
   *
   * @return returns the player's color
   */
  PlayerColor getPlayersColor();

  /**
   * Returns the player's hand.
   *
   * @return the player's hand
   */
  List<Cards> getHand();

  /**
   * Removes a PlayingCard object at the given index and returns it.
   *
   * @param index the index at which to remove the card from
   * @return the removed PlayingCard
   */
  Cards removeCardAtIndex(int index);

  /**
   * Sets an action listener for the AI player's actions.
   *
   * @param listener the listener to associated with this AI player
   */
  void addActionListener(Features listener);
}