package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.providers.model.AttackValue;

/**
 * Class to help convert from the AttackValue enum to the CardNumbers enum.
 */
public class ProviderAttackValueToMainCardNumbers implements ConvertEnums<CardNumbers> {

  private final AttackValue providerNumber;

  /**
   * ProviderAttackValueToMainCardNumbers class constructor.
   *
   * @param providerNumber the AttackValue to translate from
   */
  public ProviderAttackValueToMainCardNumbers(AttackValue providerNumber) {
    if (providerNumber == null) {
      throw new IllegalArgumentException("The provider number must not be null.");
    }

    this.providerNumber = providerNumber;
  }

  /**
   * Converts from the AttackValue enum to the CardNumbers enum.
   *
   * @return the translated CardNumbers enum value which we translated from the AttackValue enum
   * value
   */
  @Override
  public CardNumbers convertEnums() {
    return providerAttackValueToMainCardNumber();
  }

  /**
   * Helper class to perform the switch from the AttackValue enum value to the CardNumber enum
   * value.
   *
   * @return the translated CardNumber enum value which we translated from the AttackValue
   * enum value
   */
  public CardNumbers providerAttackValueToMainCardNumber() {
    switch (providerNumber) {
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
