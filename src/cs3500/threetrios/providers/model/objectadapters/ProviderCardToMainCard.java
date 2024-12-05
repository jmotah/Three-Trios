package cs3500.threetrios.providers.model.objectadapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.providers.model.AttackValue;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.CardDirection;
import cs3500.threetrios.providers.model.enumadapters.ConvertEnums;
import cs3500.threetrios.providers.model.enumadapters.MainCardCompassToProviderCardDirection;
import cs3500.threetrios.providers.model.enumadapters.ProviderAttackValueToMainInteger;

/**
 * Adapts the provider's Card interface and class implementation to the project's main Cards
 * interface. Adapts the provider's implementation to the primary interface.
 */
public class ProviderCardToMainCard implements Cards {

  private final Card providerCard;

  /**
   * ProviderCardToMainCard class constructor.
   *
   * @param providerCard the provider Card interface implementation class to delegate to
   */
  public ProviderCardToMainCard(Card providerCard) {
    if (providerCard == null) {
      throw new IllegalArgumentException("Provider card cannot be null");
    }

    this.providerCard = providerCard;
  }

  /**
   * Gets the name of a card object by delegating.
   *
   * @return the name of the playing card
   */
  @Override
  public String getName() {
    return providerCard.getCard();
  }

  /**
   * Gets the value of a card object at a specific direction by delegating.
   *
   * @param direction the specific direction to grab the value from
   * @return the integer value of the PlayingCard object at the specific direction
   */
  @Override
  public int getValue(CardCompass direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }

    ConvertEnums<CardDirection> cardCompassToCardDirection =
            new MainCardCompassToProviderCardDirection(direction);

    AttackValue attackValue =
            providerCard.getAttackValue(cardCompassToCardDirection.convertEnums());

    ConvertEnums<Integer> attackValueToMainInteger =
            new ProviderAttackValueToMainInteger(attackValue);

    return attackValueToMainInteger.convertEnums();
  }

  /**
   * Gets the value as a string of the PlayingCard object at a specific direction by delegating.
   * This method is primarily used to consider the "A" case.
   *
   * @param direction the specific direction to grab the value from
   * @return the String value of the PlayingCard object at the specific direction, accounting for A
   */
  @Override
  public String getValueAsString(CardCompass direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }

    return convertIntValueToString(this.getValue(direction));
  }

  /**
   * Helper method to convert an integer value to its respective String value. This method is
   * primarily used to consider the "A" case.
   *
   * @param value the integer value to convert
   * @return the respective String value given an integer value.
   */
  private String convertIntValueToString(int value) {
    if (value < 1 || value > 10) {
      throw new IllegalArgumentException("Value must be an integer between [1,10]");
    }

    switch (value) {
      case 1:
        return "1";
      case 2:
        return "2";
      case 3:
        return "3";
      case 4:
        return "4";
      case 5:
        return "5";
      case 6:
        return "6";
      case 7:
        return "7";
      case 8:
        return "8";
      case 9:
        return "9";
      case 10:
        return "A";
      default:
        throw new IllegalArgumentException("Value must be an integer between [1,10]");
    }
  }
}