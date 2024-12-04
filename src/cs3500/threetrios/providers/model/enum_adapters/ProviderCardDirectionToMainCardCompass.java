package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.providers.model.CardDirection;

public class ProviderCardDirectionToMainCardCompass implements ConvertEnums<CardCompass> {

  private final CardDirection direction;

  public ProviderCardDirectionToMainCardCompass(CardDirection direction) {
    this.direction = direction;
  }

  @Override
  public CardCompass convertEnums() {
    return providerCardDirectionToMainCardCompass(direction);
  }

  private CardCompass providerCardDirectionToMainCardCompass(CardDirection providerDirection) {
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
