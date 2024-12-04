package cs3500.threetrios.providers.model.object_adapters;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.enum_adapters.ConvertEnums;
import cs3500.threetrios.providers.model.enum_adapters.MainPlayerColorToProviderPlayerColor;
import cs3500.threetrios.providers.model.enum_adapters.ProviderPlayerColorToMainPlayerColor;

public class ProviderHumanToMainHuman implements Players {

  private final Player providerHuman;

  public ProviderHumanToMainHuman(Player providerHuman) {
    this.providerHuman = providerHuman;
  }

  @Override
  public PlayerColor getPlayersColor() {
    ConvertEnums<PlayerColor> providerColorToMainColor =
            new ProviderPlayerColorToMainPlayerColor(providerHuman.getColor());

    return providerColorToMainColor.convertEnums();
  }

  @Override
  public List<Cards> getHand() {
    List<Cards> mainHand = new ArrayList<>();

    for (int i = 0; i < providerHuman.getHand().size(); i++) {
      ProviderCardToMainCard providerCardToMainCard = new ProviderCardToMainCard(
              providerHuman.getHand().get(i));

      mainHand.add(providerCardToMainCard);
    }
    return mainHand;
  }

  @Override
  public Cards removeCardAtIndex(int index) {
    Card removedCard = providerHuman.getHand().get(index);

    return new ProviderCardToMainCard(removedCard);
  }

  @Override
  public void addActionListener(Features listener) {

  }
}
