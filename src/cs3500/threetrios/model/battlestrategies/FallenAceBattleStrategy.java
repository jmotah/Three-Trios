package cs3500.threetrios.model.battlestrategies;

public class FallenAceBattleStrategy implements BattleStrategies {
  @Override
  public boolean shouldCardFlip(int attackerValue, int defenderValue) {
    if (attackerValue == 1 && defenderValue == 10) {
      return true;
    } else if (attackerValue == 10 && defenderValue == 1) {
      return false;
    }
    return attackerValue > defenderValue;
  }
}
