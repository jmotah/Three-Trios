package cs3500.threetrios.model.battlestrategies;

/**
 * Represents the "Fallen Ace" battle strategy for battling. This strategy is if an attacker's
 * value is 1 and the defender has an A, then the 1 will take priority over the A causing the
 * defender's grid tile to flip.
 */
public class FallenAceBattleStrategy implements BattleStrategies {

  /**
   * Checks if the attacker's value is equal to 1 and the defender's value is equal to 10. If this
   * case is true, then the defender's card should flip. However, if the attacker's value is 10 and
   * the defender's value is 1, then no cards should be flipped. For every other case, the normal
   * battle strategy is delegated to.
   *
   * @param attackerValue the integer value the attacker is attacking with
   * @param defenderValue the integer value the defender is attacking with
   * @return true if the attacker's value is 1 and the defenders value is 10; false if the
   * attacker's value is 10 and the defender's value is 1; for all else the normal strategy is
   * consulted to determine whether the card should be flipped
   */
  @Override
  public boolean shouldCardFlip(int attackerValue, int defenderValue) {
    if (attackerValue == 1 && defenderValue == 10) {
      return true;
    } else if (attackerValue == 10 && defenderValue == 1) {
      return false;
    }
    BattleStrategies normalStrategy = new NormalBattleStrategy();
    return normalStrategy.shouldCardFlip(attackerValue, defenderValue);
  }
}
