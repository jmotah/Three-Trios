package cs3500.threetrios.providers.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.providers.Utilities;
import cs3500.threetrios.providers.controller.ThreeTriosFeatures;

public class ProviderHumanAdapter implements Player {

  private final Players player;
  private final Utilities utilities;

  public ProviderHumanAdapter(PlayerColor color, List<Card> hand) {
    if (color == null || hand == null) {
      throw new NullPointerException("Color and/or hand cannot be null!");
    }

    this.utilities = new Utilities();

    this.player =
            new cs3500.threetrios.model.player.Player(
                    utilities.providerPlayerColorToMainPlayerColor(color),
                    utilities.providerListCardToMainListPlayingCard(hand));
  }

  @Override
  public PlayerColor getColor() {
    return utilities.mainColorToProvidedPlayerColor(player.getPlayersColor());
  }

  @Override
  public List<Card> getHand() {
    return utilities.mainPlayingCardListToProviderCardList(player.getHand());
  }

  @Override
  public void addCardToHand(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null!");
    }

    PlayingCard playingCard = utilities.providerCardToMainPlayerCard(card);
    player.getHand().add(playingCard);
  }

  @Override
  public void removeCardFromHand(Card card) {
    int index = -1;

    for (int i = 0; i < getHand().size(); i++) {
      if (getHand().get(i).equals(card)) {
        index = i;
      }
    }

    if (index < 0) {
      throw new IllegalArgumentException("Card is not found within the player's hand!");
    }

    player.removeCardAtIndex(index);
  }

  @Override
  public void addActionListener(ThreeTriosFeatures listener) {
    return;
  }

  @Override
  public void notifyTurn() {
    return;
  }
}