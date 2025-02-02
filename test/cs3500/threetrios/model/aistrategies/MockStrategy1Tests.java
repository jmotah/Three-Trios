package cs3500.threetrios.model.aistrategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.util.HashMap;

import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Represents tests with the MockModelStrategy1 class as the mock model.
 */
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