package cs3500.threetrios.model.battlestrategies;

/**
 * Represents the normal/standard battle strategy for battling. The strategy is if an attacker's
 * value is greater than the defender's value, then the defender's card will flip.
 */
public class NormalBattleStrategy implements BattleStrategies {

  /**
   * Checks if the attacker's value is greater than the defender's value. If it is, then the
   * defender's card should be flipped.
   *
   * @param attackerValue the integer value the attacker is attacking with
   * @param defenderValue the integer value the defender is attacking with
   * @return true if the attacker's value is greater than the defender's value, false if otherwise
   */
  @Override
  public boolean shouldCardFlip(int attackerValue, int defenderValue) {
    return attackerValue > defenderValue;
  }
}
