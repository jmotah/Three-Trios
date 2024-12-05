package cs3500.threetrios.model.battlestrategies;

public interface BattleStrategies {

  /**
   * Determines if the attacker should flip the card or not.
   *
   * @param attackerValue the integer value the attacker is attacking with
   * @param defenderValue the integer value the defender is attacking with
   * @return true if the attacker should flip the card, false if otherwise
   */
  boolean shouldCardFlip(int attackerValue, int defenderValue);

}
