package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.providers.model.AttackValue;

public class MainIntegerToProviderAttackValue implements ConvertEnums<AttackValue> {

  int cardNumber;

  public MainIntegerToProviderAttackValue(int cardNumber) {
    this.cardNumber = cardNumber;
  }

  @Override
  public AttackValue convertEnums() {
    return mainCardNumberIntToProviderAttackValue(cardNumber);
  }

  private AttackValue mainCardNumberIntToProviderAttackValue(int mainIntegerCardNumberValue) {
    switch (mainIntegerCardNumberValue) {
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
