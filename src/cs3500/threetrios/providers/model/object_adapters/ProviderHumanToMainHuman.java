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
import cs3500.threetrios.providers.model.enum_adapters.ProviderPlayerColorToMainPlayerColor;

/**
 * Adapts the provider's Player interface and class implementation to the project's main Players
 * interface. Adapts the provider's implementation to the primary interface.
 */
public class ProviderHumanToMainHuman implements Players {

  private final Player providerHuman;

  /**
   * ProviderHumanToMainHuman class constructor.
   *
   * @param providerHuman the provider's Player interface implementation class to delegate to
   */
  public ProviderHumanToMainHuman(Player providerHuman) {
    if (providerHuman == null) {
      throw new IllegalArgumentException("Provided player cannot be null");
    }

    this.providerHuman = providerHuman;
  }

  /**
   * Returns the player's color by delegating.
   *
   * @return returns the player's color
   */
  @Override
  public PlayerColor getPlayersColor() {
    ConvertEnums<PlayerColor> providerColorToMainColor =
            new ProviderPlayerColorToMainPlayerColor(providerHuman.getColor());

    return providerColorToMainColor.convertEnums();
  }

  /**
   * Returns the player's hand by delegating.
   *
   * @return the player's hand
   */
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

  /**
   * Removes a PlayingCard object at the given index and returns it by delegating.
   *
   * @param index the index at which to remove the card from
   * @return the removed PlayingCard
   */
  @Override
  public Cards removeCardAtIndex(int index) {
    if (index < 0 || index >= providerHuman.getHand().size()) {
      throw new IllegalArgumentException("Index out of bounds");
    }

    Card removedCard = providerHuman.getHand().get(index);

    return new ProviderCardToMainCard(removedCard);
  }

  /**
   * Sets an action listener for the AI player's actions by delegating.
   *
   * @param listener the listener to associated with this AI player
   */
  @Override
  public void addActionListener(Features listener) {
    providerHuman.addActionListener(
            new MainFeaturesToProviderFeatures(listener));
  }
}