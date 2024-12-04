package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.providers.model.AttackValue;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.CardDirection;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.enum_adapters.ConvertEnums;
import cs3500.threetrios.providers.model.enum_adapters.MainIntegerToProviderAttackValue;
import cs3500.threetrios.providers.model.enum_adapters.ProviderCardDirectionToMainCardCompass;

/**
 * Adapts the project's main Cards interface and class implementation to the provider's Card
 * interface. Adapts the primary implementation to a provider's interface.
 */
public class MainCardToProviderCard implements Card {

  private final Cards card;

  private PlayerColor colorOwner;

  /**
   * MainCardToProviderCard class constructor.
   *
   * @param card the main Cards interface implementation class to delegate to
   */
  public MainCardToProviderCard(Cards card) {
    this.card = card;

    this.colorOwner = null;
  }

  /**
   * Retrieves the unique identifier for this card by delegating.
   *
   * @return a String representing the card ID
   */
  @Override
  public String getCard() {
    return card.getName();
  }

  /**
   * Gets the attack value for this card in the specified direction by delegating.
   *
   * @param direction the direction for which to get the attack value
   * @return the AttackValue corresponding to the specified direction
   * @throws IllegalArgumentException if the direction is null
   */
  @Override
  public AttackValue getAttackValue(CardDirection direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }

    if (card == null) {
      return AttackValue.ONE;
    }

    ConvertEnums<CardCompass> cardDirectionToCardCompass =
            new ProviderCardDirectionToMainCardCompass(direction);

    int value = card.getValue(cardDirectionToCardCompass.convertEnums());

    ConvertEnums<AttackValue> integerToAttackValue =
            new MainIntegerToProviderAttackValue(value);

    return integerToAttackValue.convertEnums();
  }

  /**
   * Retrieves the color of the player who owns this card.
   *
   * @return the PlayerColor representing the card owner
   */
  @Override
  public PlayerColor getCardOwner() {
    return colorOwner;
  }

  /**
   * Sets the owner of this card to the specified player color.
   *
   * @param color the PlayerColor to assign as the owner of the card
   */
  @Override
  public void setCardOwner(PlayerColor color) {
    this.colorOwner = color;
  }
}