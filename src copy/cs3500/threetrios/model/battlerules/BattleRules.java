package cs3500.threetrios.model.battlerules;

import java.awt.Point;
import java.util.List;

import cs3500.threetrios.model.grid.Grid;

/**
 * Represents an interface for battle rules. These rules are performed before the combo step only.
 * They analyze the adjacent cells only and calculate respective rule criteria based on these cells.
 * These rules can only be satisfied if there are at least two adjacent cards to that of the
 * placed card that satisfy the rule.
 */
public interface BattleRules {

  /**
   * Applies the respective battle rule to the model grid to a specifically placed card at the
   * provided row and column indices. Finds all the card positions that satisfy the respective rule
   * and returns them in a list of Point objects.
   *
   * @param row    the row index of the placed card
   * @param column the column index of the placed card
   * @param grid   the current game grid to consult
   * @return a list of Point objects representing the row and column positions of each card to be
   *     flipped
   */
  List<Point> applyRule(int row, int column, Grid[][] grid);
}
