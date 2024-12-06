package cs3500.threetrios.model.battlestrategies;

/**
 * An interface for battle strategies focusing on various strategies to determine whether a card
 * should be flipped during the battle phase or not.
 */
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
