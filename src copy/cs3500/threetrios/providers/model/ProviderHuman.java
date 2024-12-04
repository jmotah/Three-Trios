package cs3500.threetrios.providers.model;

import java.util.List;

import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.providers.controller.ThreeTriosFeatures;

public class ProviderHuman implements Player {

  private final Players player;

  public ProviderHuman(PlayerColor color, List<Card> hand) {
    Players player = new Player(color, hand);
  }

  @Override
  public PlayerColor getColor() {
    return player.getPlayersColor();
  }

  @Override
  public List<Card> getHand() {
    return player.getPlayersColor();
  }

  @Override
  public void addCardToHand(Card card) {

  }

  @Override
  public void removeCardFromHand(Card card) {
    int index = -1;

    for(int i = 0; i < getHand().size(); i++) {
      if(getHand().get(i).equals(card)) {
        index = i;
      }
    }

    if(index < 0) {
      throw new IllegalArgumentException("Card is not found within the player's hand!");
    }

    player.removeCardAtIndex(index);
  }

  @Override
  public void addActionListener(ThreeTriosFeatures listener) {
    player.addActionListener(listener);
  }

  @Override
  public void notifyTurn() {
    return;
  }
}
