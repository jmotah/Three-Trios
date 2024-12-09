package cs3500.threetrios.providers.model.enumadapters;

import cs3500.threetrios.providers.model.AttackValue;

/**
 * Class to help convert from the AttackValue enum to an integer value.
 */
public class ProviderAttackValueToMainInteger implements ConvertEnums<Integer> {

  private final AttackValue attackValue;

  /**
   * ProviderAttackValueToMainInteger class constructor.
   *
   * @param attackValue the AttackValue enum value to translate from
   */
  public ProviderAttackValueToMainInteger(AttackValue attackValue) {
    if (attackValue == null) {
      throw new IllegalArgumentException("Attack value cannot be null!");
    }

    this.attackValue = attackValue;
  }

  /**
   * Converts from the AttackValue enum to an integer.
   *
   * @return the translated integer value which we translated from the AttackValue
   *     enum value
   */
  @Override
  public Integer convertEnums() {
    return providerAttackValueToMainInteger();
  }

  /**
   * Helper class to perform the switch from the AttackValue enum value to integer value.
   *
   * @return the translated integer value which we translated from the AttackValue
   *     enum value
   */
  private int providerAttackValueToMainInteger() {
    switch (attackValue) {
      case ONE:
        return 1;
      case TWO:
        return 2;
      case THREE:
        return 3;
      case FOUR:
        return 4;
      case FIVE:
        return 5;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case A:
        return 10;
      default:
        throw new IllegalArgumentException("Given attack value cannot be converted to an integer!");
    }
  }
}