package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.providers.model.AttackValue;

public class ProviderAttackValueToMainInteger implements ConvertEnums<Integer> {

  private final AttackValue attackValue;

  public ProviderAttackValueToMainInteger(AttackValue attackValue) {
    this.attackValue = attackValue;
  }

  @Override
  public Integer convertEnums() {
    return providerAttackValueToMainInteger(attackValue);
  }

  private int providerAttackValueToMainInteger(AttackValue attackValue) {
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
    }
    throw new IllegalArgumentException("Given attack value cannot be converted to an integer!");
  }
}