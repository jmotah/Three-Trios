package cs3500.threetrios.providers.model.object_adapters;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.providers.controller.ThreeTriosFeatures;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.enum_adapters.ConvertEnums;
import cs3500.threetrios.providers.model.enum_adapters.MainPlayerColorToProviderPlayerColor;

public class MainHumanToProviderHuman implements Player {

  private final Players human;

  public MainHumanToProviderHuman(Players human) {
    this.human = human;
  }

  @Override
  public PlayerColor getColor() {
    ConvertEnums<PlayerColor> mainColorToProviderColor =
            new MainPlayerColorToProviderPlayerColor(human.getPlayersColor());

    return mainColorToProviderColor.convertEnums();
  }

  @Override
  public List<Card> getHand() {
    List<Card> providerHand = new ArrayList<Card>();

    for (int i = 0; i < human.getHand().size(); i++) {
      Card providerCard = new MainCardToProviderCard(human.getHand().get(i));
      providerHand.add(providerCard);
    }

    return providerHand;
  }

  @Override
  public void addCardToHand(Card card) {
    this.getHand().add(card);
  }

  @Override
  public void removeCardFromHand(Card card) {
    this.getHand().remove(card);
  }

  @Override
  public void addActionListener(ThreeTriosFeatures listener) {

  }

  @Override
  public void notifyTurn() {

  }
}
