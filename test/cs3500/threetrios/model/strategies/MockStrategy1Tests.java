package cs3500.threetrios.model.strategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import cs3500.threetrios.model.ThreeTriosModel;

public class MockStrategy1Tests {
  ThreeTriosModel model;

  Strategies strategy;

  @Before
  public void setup() {
    model = new MockModelStrategy1();

    strategy = new Strategy1(model);
  }

  @Test
  public void testEmptyCornersAllElseFilled() {
    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(1, 1), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }
}