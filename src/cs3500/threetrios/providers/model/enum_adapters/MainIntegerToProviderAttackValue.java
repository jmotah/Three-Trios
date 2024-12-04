package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.providers.model.AttackValue;

/**
 * Class to help convert from an Integer value to the AttackValue enum.
 */
public class MainIntegerToProviderAttackValue implements ConvertEnums<AttackValue> {

  private final int cardNumber;

  /**
   * MainIntegerToProviderAttackValue class constructor.
   *
   * @param cardNumber the integer we want to translate from
   */
  public MainIntegerToProviderAttackValue(int cardNumber) {
    if (cardNumber < 1 || cardNumber > 10) {
      throw new IllegalArgumentException("Card number must be between 1 and 10");
    }

    this.cardNumber = cardNumber;
  }

  /**
   * Converts from an integer value to the AttackValue enum.
   *
   * @return the translated AttackValue enum value which we translated from an integer value
   */
  @Override
  public AttackValue convertEnums() {
    return mainCardNumberIntToProviderAttackValue();
  }

  /**
   * Helper class to perform the switch from an integer value to the AttackValue enum
   * value.
   *
   * @return the translated AttackValue enum value which we translated from an integer value
   */
  private AttackValue mainCardNumberIntToProviderAttackValue() {
    switch (cardNumber) {
      case 1:
        return AttackValue.ONE;
      case 2:
        return AttackValue.TWO;
      case 3:
        return AttackValue.THREE;
      case 4:
        return AttackValue.FOUR;
      case 5:
        return AttackValue.FIVE;
      case 6:
        return AttackValue.SIX;
      case 7:
        return AttackValue.SEVEN;
      case 8:
        return AttackValue.EIGHT;
      case 9:
        return AttackValue.NINE;
      case 10:
        return AttackValue.A;
      default:
        throw new IllegalArgumentException("Given integer cannot be negative or greater than 10!");
    }
  }
}