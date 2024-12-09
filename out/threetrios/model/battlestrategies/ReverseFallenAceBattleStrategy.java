package cs3500.threetrios.model.battlestrategies;

/**
 * Represents a combination of the "Reverse" and "Fallen Ace" battle strategies for battling. The
 * strategy is if an attacker's value is equal to 10 and the defender's value is equal to 1
 * (reverse of "Fallen Ace" strategy), then the defender's card will flip. The opposite of this
 * does not flip the defender's card. For all other cases, the "Reverse" battle strategy is
 * consulted to determine to flip the defender's card or not.
 */
public class ReverseFallenAceBattleStrategy implements BattleStrategies {

  /**
   * Checks if the attacker's value is equal to 10 and the defender's value is equal to 1. If this
   * case is true, then the defender's card should flip. However, if the attacker's value is 1 and
   * the defender's value is 10, then no cards should be flipped. For every other case, the
   * "Reverse" battle strategy is delegated to.
   *
   * @param attackerValue the integer value the attacker is attacking with
   * @param defenderValue the integer value the defender is attacking with
   * @return true if the attacker's value is 10 and the defender's value is 1; false if the
   * attacker's value is 1 and the defender's value is 10; for all else the "Reverse" strategy
   * is consulted to determine whether card should be flipped
   */
  @Override
  public boolean shouldCardFlip(int attackerValue, int defenderValue) {
    if (attackerValue == 1 && defenderValue == 10) {
      return false;
    }
    if (attackerValue == 10 && defenderValue == 1) {
      return true;
    }

    ReverseBattleStrategy reverseBattleStrategy = new ReverseBattleStrategy();

    return reverseBattleStrategy.shouldCardFlip(attackerValue, defenderValue);
  }
}
