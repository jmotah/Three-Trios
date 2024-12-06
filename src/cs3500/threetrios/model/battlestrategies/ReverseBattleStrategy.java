package cs3500.threetrios.model.battlestrategies;

/**
 * Represents the "Reverse" battle strategy for battling. This strategy is if an attacker's value
 * is less than the defender's value, then the defender's card will flip.
 */
public class ReverseBattleStrategy implements BattleStrategies {

  /**
   * Checks if the attacker's value is less than the defender's value. If it is, then the
   * defender's card should be flipped.
   *
   * @param attackerValue the integer value the attacker is attacking with
   * @param defenderValue the integer value the defender is attacking with
   * @return true if the attacker's value is less than the defender's value; false if otherwise
   */
  @Override
  public boolean shouldCardFlip(int attackerValue, int defenderValue) {
    return attackerValue < defenderValue;
  }
}
