package cs3500.threetrios.player;

import java.util.List;

import cs3500.threetrios.cards.PlayingCard;

/**
 * Represents a type of player which possesses a player color and a hand of PlayingCard objects.
 */
public interface Player {

  PlayerColor getPlayersColor();

  List<PlayingCard> getHand();

  PlayingCard removeCardAtIndex(int index);
}
