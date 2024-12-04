package cs3500.threetrios.providers.model.object_adapters;

import java.awt.*;
import java.util.HashMap;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.strategies.Strategy2;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.Strategies;
import cs3500.threetrios.providers.model.ThreeTriosStrategies;

public class MainStrategy2ToProviderStrategy2 implements Strategies {
  private final cs3500.threetrios.model.strategies.Strategies strategy;

  public MainStrategy2ToProviderStrategy2(ReadonlyThreeTriosModel model) {
    this.strategy = new Strategy2(model);
  }

  @Override
  public ThreeTriosStrategies.Move getMove(Player player) {
    HashMap<Point, Integer> move = strategy.runStrategy();

    Point point = move.keySet().iterator().next();
    int row = (int) point.getX();
    int column = (int) point.getY();
    int cardIdx = move.get(point);

    return new ThreeTriosStrategies.Move(row, column, cardIdx);
  }
}
