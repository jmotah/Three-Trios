package cs3500.threetrios.model.battlerules;

import java.awt.*;
import java.util.List;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.grid.Grid;

public class PlusBattleRule implements BattleRules {

  @Override
  public List<Point> applyRule(int row, int column, Grid[][] grid) {
    return List.of();
  }
}
