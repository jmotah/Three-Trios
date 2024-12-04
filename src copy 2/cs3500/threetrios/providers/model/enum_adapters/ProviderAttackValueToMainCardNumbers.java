package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.providers.model.AttackValue;

public class ProviderAttackValueToMainCardNumbers implements ConvertEnums<CardNumbers> {

  private final AttackValue providerNumber;

  public ProviderAttackValueToMainCardNumbers(AttackValue providerNumber) {
    this.providerNumber = providerNumber;
  }

  @Override
  public CardNumbers convertEnums() {
    return providerAttackValueToMainCardNumber(providerNumber);
  }

  public CardNumbers providerAttackValueToMainCardNumber(AttackValue providerAttackValue) {
    switch (providerAttackValue) {
      case ONE:
        return CardNumbers.ONE;
      case TWO:
        return CardNumbers.TWO;
      case THREE:
        return CardNumbers.THREE;
      case FOUR:
        return CardNumbers.FOUR;
      case FIVE:
        return CardNumbers.FIVE;
      case SIX:
        return CardNumbers.SIX;
      case SEVEN:
        return CardNumbers.SEVEN;
      case EIGHT:
        return CardNumbers.EIGHT;
      case NINE:
        return CardNumbers.NINE;
      case A:
        return CardNumbers.A;
      default:
        throw new IllegalArgumentException("Invalid attack value!");
    }
  }

}
