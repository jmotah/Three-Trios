package cs3500.threetrios.player;

import java.util.List;

import cs3500.threetrios.cards.PlayingCard;

/**
 * Represents a non-computer controlled player. Possessing a color representing their player
 * and a hand of cards.
 */
public class HumanPlayer implements Player {

  private final PlayerColor playerType;

  private final List<PlayingCard> hand;

  /**
   * A HumanPlayer class constructor.
   *
   * @param color the color of the player
   * @param hand  the player's hand
   */
  public HumanPlayer(PlayerColor color, List<PlayingCard> hand) {
    if (color == null) {
      throw new IllegalArgumentException("The player color cannot be null!");
    } else if (hand == null) {
      throw new IllegalArgumentException("The player hand cannot be null!");
    }

    this.playerType = color;
    this.hand = hand;
  }

  /**
   * Returns the player's color.
   *
   * @return returns the player's color
   */
  @Override
  public PlayerColor getPlayersColor() {
    return playerType;
  }

  /**
   * Returns the player's hand.
   *
   * @return the player's hand
   */
  @Override
  public List<PlayingCard> getHand() {
    return hand;
  }

  /**
   * Removes a PlayingCard object at the given index and returns it.
   *
   * @param index the index at which to remove the card from
   * @return the removed PlayingCard
   */
  @Override
  public PlayingCard removeCardAtIndex(int index) {
    if (index < 0 || index >= hand.size()) {
      throw new IllegalArgumentException("The index out of bounds!");
    }

    return this.hand.remove(index);
  }
}