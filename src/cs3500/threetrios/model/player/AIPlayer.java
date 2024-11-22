package cs3500.threetrios.model.player;

import java.util.List;

import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.strategies.Strategies;

public class AIPlayer extends Player implements AIPlayers {
  private Strategies strategy;

  public AIPlayer(PlayerColor color, List<PlayingCard> hand, Strategies strategy) {
    super(color, hand);
    this.strategy = strategy;
  }

  @Override
  public PlayerColor getPlayersColor() {
    return super.getPlayersColor();
  }

  @Override
  public List<PlayingCard> getHand() {
    return super.getHand();
  }

  @Override
  public PlayingCard removeCardAtIndex(int index) {
    return super.removeCardAtIndex(index);
  }

  @Override
  public Strategies getStrategies() {
    return strategy;
  }
}
