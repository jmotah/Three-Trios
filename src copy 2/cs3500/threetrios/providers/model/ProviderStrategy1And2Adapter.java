package cs3500.threetrios.providers.model;

import java.awt.*;
import java.util.HashMap;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.strategies.Strategy1And2;

public class ProviderStrategy1And2Adapter implements Strategies {

  private final cs3500.threetrios.model.strategies.Strategies strategy;

  public ProviderStrategy1And2Adapter(ReadonlyThreeTriosModel model) {
    this.strategy = new Strategy1And2(model);
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