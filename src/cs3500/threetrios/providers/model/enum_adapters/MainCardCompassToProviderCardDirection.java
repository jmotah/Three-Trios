package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.providers.model.CardDirection;

public class MainCardCompassToProviderCardDirection implements ConvertEnums<CardDirection> {

  private final CardCompass cardCompass;

  public MainCardCompassToProviderCardDirection(CardCompass cardCompass) {
    this.cardCompass = cardCompass;
  }

  @Override
  public CardDirection convertEnums() {
    return providerCardDirectionToMainCardCompass(cardCompass);
  }

  private CardDirection providerCardDirectionToMainCardCompass(CardCompass cardCompass) {
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