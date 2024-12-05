package cs3500.threetrios.model.battlestrategies;

public class NormalBattleStrategy implements BattleStrategies {
  @Override
  public boolean shouldCardFlip(int attackerValue, int defenderValue) {
    return attackerValue > defenderValue;
  }
}
