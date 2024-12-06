package cs3500.threetrios.model.battlerules;

import java.awt.*;
import java.util.List;

import cs3500.threetrios.model.grid.Grid;

public interface BattleRules {

  List<Point> applyRule(int row, int column, Grid[][] grid);

}
