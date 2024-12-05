package cs3500.threetrios.providers.model.enumadapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.providers.model.CardDirection;

/**
 * Class to help convert from the CardDirection enum to the CardCompass enum.
 */
public class ProviderCardDirectionToMainCardCompass implements ConvertEnums<CardCompass> {

  private final CardDirection direction;

  /**
   * ProviderCardDirectionToMainCardCompass class constructor.
   *
   * @param direction the CardDirection enum value to translate from
   */
  public ProviderCardDirectionToMainCardCompass(CardDirection direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }

    this.direction = direction;
  }

  /**
   * Converts from the CardDirection enum to the CardCompass enum.
   *
   * @return the translated CardCompass enum value which we translated from the CardDirection
   *     enum value
   */
  @Override
  public CardCompass convertEnums() {
    return providerCardDirectionToMainCardCompass();
  }

  /**
   * Helper class to perform the switch from the CardDirection enum value to the CardCompass enum
   * value.
   *
   * @return the translated CardCompass enum value which we translated from the CardDirection
   *     enum value
   */
  private CardCompass providerCardDirectionToMainCardCompass() {
    switch (direction) {
      case NORTH:
        return CardCompass.NORTH_VALUE;
      case SOUTH:
        return CardCompass.SOUTH_VALUE;
      case EAST:
        return CardCompass.EAST_VALUE;
      case WEST:
        return CardCompass.WEST_VALUE;
      default:
        throw new IllegalArgumentException("Invalid direction!");
    }
  }

}
