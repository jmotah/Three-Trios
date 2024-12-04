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

/**
 * Adapts the project's main Players interface and class implementation to the provider's Player
 * interface. Adapts the primary implementation to a provider's interface.
 */
public class MainHumanToProviderHuman implements Player {

  private final Players human;

  /**
   * MainHumanToProviderHuman class constructor.
   *
   * @param human the main Players interface implementation class to delegate to
   */
  public MainHumanToProviderHuman(Players human) {
    if (human == null) {
      throw new IllegalArgumentException("human cannot be null");
    }

    this.human = human;
  }

  /**
   * Retrieves the color of the player by delegating.
   *
   * @return The PlayerColor representing the player's color.
   */
  @Override
  public PlayerColor getColor() {
    ConvertEnums<PlayerColor> mainColorToProviderColor =
            new MainPlayerColorToProviderPlayerColor(human.getPlayersColor());

    return mainColorToProviderColor.convertEnums();
  }

  /**
   * Retrieves the list of cards currently in the player's hand by delegating.
   *
   * @return A list of Card objects representing the player's hand.
   */
  @Override
  public List<Card> getHand() {
    List<Card> providerHand = new ArrayList<>();

    for (int i = 0; i < human.getHand().size(); i++) {
      Card providerCard = new MainCardToProviderCard(human.getHand().get(i));
      providerHand.add(providerCard);
    }

    return providerHand;
  }

  /**
   * Adds a card to the player's hand by delegating.
   *
   * @param card The Card object to be added.
   * @throws IllegalArgumentException if the card is null.
   */
  @Override
  public void addCardToHand(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("card cannot be null");
    }

    this.getHand().add(card);
  }

  /**
   * Removes a card from the player's hand by delegating.
   *
   * @param card The Card object to be removed.
   * @throws IllegalArgumentException if the card is null or not in the hand.
   */
  @Override
  public void removeCardFromHand(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("card cannot be null");
    }

    this.getHand().remove(card);
  }

  /**
   * Registers an action listener to handle the player's actions by delegating.
   *
   * @param listener The listener to register.
   */
  @Override
  public void addActionListener(ThreeTriosFeatures listener) {
    human.addActionListener(
            new ProviderFeaturesToMainFeatures(listener));
  }

  /**
   * Notifies the player that it is their turn by delegating.
   */
  @Override
  public void notifyTurn() {
    System.out.println("Something happened!");
  }
}