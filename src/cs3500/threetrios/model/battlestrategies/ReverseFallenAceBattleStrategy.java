package cs3500.threetrios.model.battlestrategies;

public class ReverseFallenAceBattleStrategy implements BattleStrategies {
  @Override
  public boolean shouldCardFlip(int attackerValue, int defenderValue) {
    if (attackerValue == 1 && defenderValue == 10) {
      return false; // Normally true for Fallen Ace, but Reverse inverts it
    }
    if (attackerValue == 10 && defenderValue == 1) {
      return true; // Normally false for Fallen Ace, but Reverse inverts it
    }

    ReverseBattleStrategy reverseBattleStrategy = new ReverseBattleStrategy();

    return reverseBattleStrategy.shouldCardFlip(attackerValue, defenderValue);
  }
}
