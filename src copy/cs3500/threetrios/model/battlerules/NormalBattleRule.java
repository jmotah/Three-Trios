package cs3500.threetrios.model.battlerules;

import java.awt.Point;
import java.util.List;

import cs3500.threetrios.model.grid.Grid;

/**
 * Represents the no-variation, or "Normal," battle rule. Nothing happens in this rule. Card
 * flipping is simply managed by the respective battle strategy.
 */
public class NormalBattleRule implements BattleRules {

  /**
   * Applies the rule for the respective rule. Since nothing should occur for the no-variation rule,
   * it simply returns null
   *
   * @param row    the row index of the placed card
   * @param column the column index of the placed card
   * @param grid   the current game grid to consult
   * @return null
   */
  @Override
  public List<Point> applyRule(int row, int column, Grid[][] grid) {
    return null;
  }
}
