package cs3500.threetrios.model.player;

import java.util.List;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.cards.PlayingCard;

/**
 * Represents a type of player which possesses a player color and a hand of PlayingCard objects.
 * This can be an AI
 */
public interface Players {

  PlayerColor getPlayersColor();

  List<PlayingCard> getHand();

  PlayingCard removeCardAtIndex(int index);

  void addActionListener(Features listener);
}