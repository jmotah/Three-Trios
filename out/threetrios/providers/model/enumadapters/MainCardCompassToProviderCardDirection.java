package cs3500.threetrios.providers.model.enumadapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.providers.model.CardDirection;

/**
 * Class to help convert from the CardCompass enum to the CardDirection enum.
 */
public class MainCardCompassToProviderCardDirection implements ConvertEnums<CardDirection> {

  private final CardCompass cardCompass;

  /**
   * MainCardCompassToProviderCardDirection enum.
   *
   * @param cardCompass the CardCompass value we want to translate from
   */
  public MainCardCompassToProviderCardDirection(CardCompass cardCompass) {
    if (cardCompass == null) {
      throw new IllegalArgumentException("The card compass cannot be null");
    }

    this.cardCompass = cardCompass;
  }

  /**
   * Converts from the CardCompass enum to the CardDirection enum.
   *
   * @return the translated CardDirection enum value which we translated from the CardCompass
   *     enum value
   */
  @Override
  public CardDirection convertEnums() {
    return providerCardDirectionToMainCardCompass();
  }

  /**
   * Helper class to perform the switch from the CardCompass enum value to the CardDirection enum
   * value.
   *
   * @return the translated CardDirection enum value which we translated from the CardCompass
   *     enum value
   */
  private CardDirection providerCardDirectionToMainCardCompass() {
    switch (cardCompass) {
      case NORTH_VALUE:
        return CardDirection.NORTH;
      case SOUTH_VALUE:
        return CardDirection.SOUTH;
      case EAST_VALUE:
        return CardDirection.EAST;
      case WEST_VALUE:
        return CardDirection.WEST;
      default:
        throw new IllegalArgumentException("Invalid direction!");
    }
  }
}