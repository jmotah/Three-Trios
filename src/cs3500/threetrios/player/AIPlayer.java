package cs3500.threetrios.player;

import java.util.List;

import cs3500.threetrios.cards.PlayingCard;

public class AIPlayer implements Player {

  PlayerColor playerType;
  List<PlayingCard> hand;

  public AIPlayer(PlayerColor playerType, List<PlayingCard> hand) {
    this.playerType = playerType;
    this.hand = hand;
  }

  @Override
  public PlayerColor getPlayersColor() {
    return playerType;
  }

  @Override
  public List<PlayingCard> getHand() {
    return hand;
  }

  @Override
  public PlayingCard removeCardAtIndex(int index) {
    return hand.remove(index);
  }
}
