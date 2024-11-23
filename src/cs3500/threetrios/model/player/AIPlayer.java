package cs3500.threetrios.model.player;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.strategies.Strategies;

/**
 * Represents an AI-controlled player in the Three Trios game. The AI player works the same as a
 * normal player, but has a specific strategy associated with it to perform its moves based off of
 * when it is their turn.
 */
public class AIPlayer extends Player implements AIPlayerListener {
  private final Strategies strategy;
  private Features listener;

  /**
   * AIPlayer class constructor,
   *
   * @param color    the color of the player
   * @param hand     the list of cards in the player's hand
   * @param strategy the strategy the AI should use
   */
  public AIPlayer(PlayerColor color, List<PlayingCard> hand, Strategies strategy) {
    super(color, hand);

    if (strategy == null) {
      throw new IllegalArgumentException("Strategy cannot be null!");
    }

    this.strategy = strategy;
  }

  /**
   * Returns the player's color.
   *
   * @return returns the player's color
   */
  @Override
  public PlayerColor getPlayersColor() {
    return super.getPlayersColor();
  }

  /**
   * Returns the player's hand.
   *
   * @return the player's hand
   */
  @Override
  public List<PlayingCard> getHand() {
    return super.getHand();
  }

  /**
   * Removes a PlayingCard object at the given index and returns it.
   *
   * @param index the index at which to remove the card from
   * @return the removed PlayingCard
   */
  @Override
  public PlayingCard removeCardAtIndex(int index) {
    return super.removeCardAtIndex(index);
  }

  /**
   * Sets an action listener for the AI player's actions.
   *
   * @param listener the listener to associated with this AI player
   */
  @Override
  public void addActionListener(Features listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null!");
    }

    this.listener = listener;
  }

  /**
   * Performs the AI player's turn. After ensuring it is this player's turn, calculates the best
   * card index and position to play to according to the AIPlayer object's associated strategy.
   *
   * @param color the color of the current player's turn
   */
  @Override
  public void performTurn(PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    }

    if (color != this.getPlayersColor()) {
      return;
    }

    new Thread(() -> {
      try {
        Thread.sleep(1000); //1 second delay
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      HashMap<Point, Integer> positionAndCardIdx = strategy.runStrategy();

      Point cellPosition = positionAndCardIdx.keySet().iterator().next();
      int cardIdx = positionAndCardIdx.get(cellPosition);

      listener.selectCard(cardIdx);
      listener.selectGridCell((int) cellPosition.getX(), (int) cellPosition.getY());
    }).start();
  }
}