package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.providers.model.AttackValue;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.CardDirection;
import cs3500.threetrios.providers.model.enum_adapters.ConvertEnums;
import cs3500.threetrios.providers.model.enum_adapters.MainCardCompassToProviderCardDirection;
import cs3500.threetrios.providers.model.enum_adapters.ProviderAttackValueToMainInteger;

public class ProviderCardToMainCard implements Cards {

  private final Card providerCard;

  public ProviderCardToMainCard(Card providerCard) {
    this.providerCard = providerCard;
  }

  @Override
  public String getName() {
    return providerCard.getCard();
  }

  @Override
  public int getValue(CardCompass direction) {
    ConvertEnums<CardDirection> cardCompassToCardDirection =
            new MainCardCompassToProviderCardDirection(direction);

    AttackValue attackValue =
            providerCard.getAttackValue(cardCompassToCardDirection.convertEnums());

    ConvertEnums<Integer> attackValueToMainInteger =
            new ProviderAttackValueToMainInteger(attackValue);

    return attackValueToMainInteger.convertEnums();
  }

  @Override
  public String getValueAsString(CardCompass direction) {
    return convertIntValueToString(this.getValue(direction));
  }

  private String convertIntValueToString(int value) {
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
