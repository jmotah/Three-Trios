package cs3500.threetrios.model.strategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import cs3500.threetrios.model.ThreeTriosModel;

import static cs3500.threetrios.ThreeTriosGame.playToGridAndBattleWithOptimalMove;

public class MockStrategy2Tests {

  ThreeTriosModel model;

  Strategies strategy;

  @Before
  public void setup() {
    model = new MockModelEmpty();

    strategy = new Strategy2(model);
  }

  @Test
  public void testEmptyCornersAllElseFilled() {
    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(0, 0), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }
}